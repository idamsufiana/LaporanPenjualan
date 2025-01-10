package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.config.JwtConfig;
import com.aegis.laporan.penjualan.constant.Group;
import com.aegis.laporan.penjualan.dto.request.LoginRequest;
import com.aegis.laporan.penjualan.dto.request.RegisterRequest;
import com.aegis.laporan.penjualan.dto.response.LoginResponse;
import com.aegis.laporan.penjualan.model.Role;
import com.aegis.laporan.penjualan.model.User;
import com.aegis.laporan.penjualan.repository.RoleRepository;
import com.aegis.laporan.penjualan.repository.UserRepository;
import com.aegis.laporan.penjualan.support.BcryptWrapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private JwtConfig jwtConfigData;
    private static final BcryptWrapper bcrypt = new BcryptWrapper(11);

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User register(RegisterRequest registerRequest) throws AuthException, ReflectionException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        try {
            User userModel = this.createUser(registerRequest);
            return userModel;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 DataIntegrityViolationException var5) {
            throw new AuthException("User Already Exsist");
        }

    }

    public User createUser(RegisterRequest registerRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        User entity = new User();
        entity.setPassword(bcrypt.hash(registerRequest.getPassword()));
        BeanUtils.copyProperties(registerRequest, entity);
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = Optional.empty();
        if(registerRequest.getRole().equals("Admin")){
            role = roleRepository.findByRole(Group.ROLE_ADMIN);
        } else if (registerRequest.getRole().equals("Kasir")) {
            role = roleRepository.findByRole(Group.ROLE_KASIR);
        }
        roles.add(role.get());
        entity.setRoles(roles);
        return entity;

    }

    public LoginResponse login(LoginRequest loginRequest) throws AuthException, ReflectionException {
        User userLogin = new User();
        User user = userRepository.findFirstByUserName(loginRequest.getUserName());
        if (!user.getStatus()) {
            throw new IllegalStateException("Account is not activated");
        }
        LoginResponse loginResponse = userLogin != null ? createToken(userLogin) : null;
        return loginResponse;
    }

    public User activateUser(String name) {
        User user = new User();
        if (userRepository.findFirstByUserName(name) != null) {
            user = userRepository.findFirstByUserName(name);
        } else{
            throw new EntityNotFoundException("User not found");
        }
        user.setStatus(true);
        return userRepository.save(user);
    }

    public LoginResponse createToken(User user) {

        Map<String, Object> tokenPayload = setPayloadToken(user);
        return new LoginResponse(createToken(tokenPayload, tokenType.AUTH_TOKEN));
    }

    public Map<String, Object> setPayloadToken(User userModel) {
        Map<String, Object> tokenPayload = new HashMap();
        tokenPayload.put("userName", userModel.getUserName());
        tokenPayload.put("role", userModel.getRoles());
        return tokenPayload;
    }

    public String createToken(Map<String, Object> payload, tokenType type) throws JWTCreationException {
        String secreet = "";
        long expiration = TimeUnit.MINUTES.toMillis((long)this.jwtConfigData.getAuthTokenLifetime());
        if (type.equals(tokenType.AUTH_TOKEN)) {
            secreet = this.jwtConfigData.getAuthTokenSecreet();
        } else if (type.equals(tokenType.REFRESH_TOKEN)) {
            expiration = TimeUnit.MINUTES.toMillis((long)this.jwtConfigData.getRefreshTokenLifetime());
            secreet = this.jwtConfigData.getRefreshTokenSecreet();
        }

        Algorithm algorithm = Algorithm.HMAC256(secreet);
        return JWT.create().withPayload(payload).withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(algorithm);
    }

    public static enum tokenType {
        AUTH_TOKEN,
        REFRESH_TOKEN;

        private tokenType() {
        }
    }


}
