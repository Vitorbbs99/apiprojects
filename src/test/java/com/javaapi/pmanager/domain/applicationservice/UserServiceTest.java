package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.repository.ProjectRepository;
import com.javaapi.pmanager.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testLoadProject() {

        String id = "3dbbss4446644-44-4555vbb";
        User userFake = new User();
        userFake.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(userFake));

        // Executar o método
        User result = userService.loadUserById(id);

        // Verificar se funcionou
        assertNotNull(result);
        assertEquals(id, result.getId());
        log.info(result.toString());
    }
}
