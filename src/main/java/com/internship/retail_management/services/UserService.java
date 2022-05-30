package com.internship.retail_management.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.retail_management.dto.ChangePasswordDTO;
import com.internship.retail_management.dto.UserAuthDTO;
import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.dto.UserInsertDTO;
import com.internship.retail_management.dto.UserLoginDTO;
import com.internship.retail_management.dto.UserUpdateDTO;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.UserRepository;
import com.internship.retail_management.services.exceptions.AuthException;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service
public class UserService {
	
	public static final Integer TOKEN_EXP = 1000 * 60 * 60 * 8; //8h
	public static final String SECRET = "cc253793-a31e-4893-9090-b973995a293a";

	@Autowired
	private UserRepository repository;

	@Autowired
	StoreService storeService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<UserDTO> findAll() {
		List<User> users = repository.findAll();
		return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	public UserDTO findById(Long id) {
		try {
			Optional<User> obj = repository.findById(id);
			return new UserDTO(obj.get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public UserDTO insert(UserInsertDTO dto) {
		try {
			User user = repository.findByEmail(dto.getEmail());
			if (user != null) {
				throw new ServiceException("Email already exists!");
			}

			user = repository.findByNif(dto.getNif());
			if (user != null) {
				throw new ServiceException("Nif already exists!");
			}

			if (dto.getNif().toString().length() != 9) {
				throw new ServiceException("Invalid Nif! Please insert a valid Nif (with 9 digits).");
			}
			// Instancia um novo user, porque na base de dados está um utilizador, e não o
			// DTO
			User obj = new User();

			persistData(obj, dto);

			obj = repository.save(obj);
		
			return new UserDTO(obj);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}

	}
	
	//Set login and generate token with user id and category on payload
	public UserAuthDTO login(UserLoginDTO obj)
	{		
		User user = repository.findByEmail(obj.getEmail());
		if (user == null) {
			throw new ServiceException("Email not found!");
		}
		
		Boolean matchPassword = passwordEncoder.matches(obj.getPassword(), user.getPassword());
		
		if (matchPassword == false)
		{
			throw new ServiceException("Invalid credentials!");
		}
		
		UserAuthDTO userAuth = new UserAuthDTO(user);
		Map<String, String> payload = new HashMap<>();
		
		payload.put("id", user.getId().toString());
		payload.put("category", user.getCategory().toString());
		payload.put("email", user.getEmail());
		payload.put("store", user.getStore().getId().toString());
		
		String token = JWT.create().withPayload(payload)
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP))
				.sign(Algorithm.HMAC512(SECRET));
		
		userAuth.setToken(token);
		
		return userAuth;
	}
	
	public UserDTO getUserByToken(String authHeader) {
			if (authHeader == null)
			{
				throw new AuthException("No token provided.");
			}
			
			String[] token = authHeader.split(" ");
			
			if (token.length != 2)
			{
				throw new AuthException("Token error.");
			}
		
			if (!token[0].equals("Bearer")|| token[1].isEmpty())
			{
				throw new AuthException("Invalid token.");
			}
			
			
		    //System.out.println(claim.);
			try {
				Algorithm algorithm = Algorithm.HMAC512(SECRET); //use more secure key
			    JWTVerifier verifier = JWT.require(algorithm)
			        .build(); //Reusable verifier instance
			    DecodedJWT jwt = verifier.verify(token[1]);
			    
			    //Get id payload from token
			    Map<String, Claim> claims = jwt.getClaims();
			    Long idUser= Long.parseLong(claims.get("id").asString());
				Optional<User> userLoggedIn = repository.findById(idUser);
				UserDTO userLoggedInDTO = new UserDTO(userLoggedIn.get());
			    return userLoggedInDTO;
			} catch (RuntimeException e) {
				throw new AuthException("Invalid token.");
			}
			
		    
		} 
		
	

	public UserDTO update(Long id, UserUpdateDTO obj) {
		try {
			User entity = repository.findById(id).get();

			if (checkEmailNif(id, obj)) {
				throw new ServiceException("There is already someone with inserted unique data (email or nif).");
			}

			if (obj.getNif().toString().length() != 9) {
				throw new ServiceException("Invalid Nif! Please insert a valid Nif (with 9 digits).");
			}
			
			persistDataUpdate(entity, obj);

			repository.save(entity);

			return new UserDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public UserDTO changePassword(Long id, ChangePasswordDTO obj) {
		User entity = repository.findById(id).get();

		persistPassword(entity, obj);
		
		repository.save(entity);
		
		return new UserDTO(entity);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	//auxiliary functions
	public User userFromUserDTO(UserDTO obj) {
		return repository.findById(obj.getId()).get();
	}

	private void persistData(User entity, UserInsertDTO obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPassword(passwordEncoder.encode(obj.getPassword()));
		entity.setPhone(obj.getPhone());
		entity.setBirthDate(obj.getBirthDate());
		entity.setNif(obj.getNif());
		entity.setCategory(obj.getCategory());
		entity.setStatus(obj.getStatus());
		entity.setAddress(obj.getAddress());
		entity.setCouncil(obj.getCouncil());
		entity.setZipCode(obj.getZipCode());
		entity.setStore(storeService.findById(obj.getStoreId()));
	}
	
	private void persistDataUpdate(User entity, UserUpdateDTO obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		entity.setBirthDate(obj.getBirthDate());
		entity.setNif(obj.getNif());
		entity.setCategory(obj.getCategory());
		entity.setStatus(obj.getStatus());
		entity.setAddress(obj.getAddress());
		entity.setCouncil(obj.getCouncil());
		entity.setZipCode(obj.getZipCode());
		entity.setStore(storeService.findById(obj.getStoreId()));
	}
	
	private void persistPassword(User entity, ChangePasswordDTO obj) {
		entity.setPassword(passwordEncoder.encode(obj.getPassword()));
	}

	public boolean checkEmailNif(Long id, UserUpdateDTO obj) {
		List<User> users = repository.findAll();
		// Deve poder alterar o seu próprio email ou nif, por isso, estes não podem
		// contar para comparação
		users.removeIf(user -> user.getId() == id);

		for (User entity : users) {
			if (entity.getEmail().equals(obj.getEmail()) || entity.getNif().equals(obj.getNif())) {
				return true;
			}
		}

		return false;

	}
}
