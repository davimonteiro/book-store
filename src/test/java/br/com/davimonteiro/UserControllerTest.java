package br.com.davimonteiro;

import br.com.davimonteiro.domain.User;
import br.com.davimonteiro.repository.UserRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MediaType contentType = MediaType.APPLICATION_JSON_UTF8;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private List<User> users;

    private User user;

    @Before
    public void setUp() throws Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

//	Cenário de aceitação (Para testes de software)
//	Dado <Algum contexto inicial>
//	Quando <Algum evento ocorrer>
//	Então <Garantir alguns resultados>


    @Test
    public void createUserTest() throws Exception {
        User user = User.builder().id(1L).name("Davi").password("1234").build();

        mvc.perform(post("/api/users")
                .contentType(contentType)
                .content(toJson(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listAllUsersTest() throws Exception {
        dadoQueEuTenho5Usuarios();
        quandoEuBuscoTodosOsUsuarios();
        entaoEuDeveriaTerUmaListaDe5Usuarios();
    }

    @Test
    public void findUserByIdTest() throws Exception {
        dadoQueEuTenho5Usuarios();
        quandoEuBuscoUmUsuarioPeloId(1L);
        entaoOIdDoUsuarioDeveiriaSerIgual(1L);
    }

    @Test
    public void updateUserTest() throws Exception {
        dadoQueEuTenhoUsuarioCom(1L, "Jose Maria da Silva", "12345");
        quandoEuBuscoAtualizoNomeUsuario(1L, "Jose Maria da Silva e Silva", "12345");
        entaoNovoNomeDoUsuarioEh(1L, "Jose Maria da Silva e Silva", "12345");
    }

    @Test
    public void deleteUserTest() throws Exception {
        dadoQueEuTenhoUsuarioCom(1L, "Jose Maria da Silva", "12345");
        quandoEuDeletarComId(1L);
        entaoNaoDevoEncontrarUsuarioComId(1L);
    }

    private void entaoNovoNomeDoUsuarioEh(Long id, String nome, String password) {
        User user = userRepository.findOne(id);
        assertEquals("O nome do usuário deveria ser.", nome, user.getName());
    }

    private void entaoNaoDevoEncontrarUsuarioComId(Long id) throws Exception {
        assertEquals("Deveria ser nulo.", null, userRepository.findOne(id));
    }

    private void quandoEuDeletarComId(Long id) throws Exception {
        mvc.perform(delete("/api/users/" + id)).andExpect(status().isNoContent());
        ;
    }

    private void quandoEuBuscoAtualizoNomeUsuario(Long id, String nome, String password) throws Exception {
        User user = User.builder().id(id).name(nome).password(password).build();

        mvc.perform(put("/api/users/" + id)
                .contentType(contentType)
                .content(toJson(user)))
                .andExpect(status().isOk());

    }

    private void dadoQueEuTenhoUsuarioCom(Long id, String nome, String password) {
        userRepository.save(User.builder().id(id).name(nome).password(password).build());
    }

    private void entaoOIdDoUsuarioDeveiriaSerIgual(Long id) {
        assertEquals("O id o usuário deveria ser", Long.valueOf(1L), user.getId());
    }

    private void quandoEuBuscoUmUsuarioPeloId(Long id) throws Exception {
        MvcResult result = mvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andReturn();

        user = gson.fromJson(result.getResponse().getContentAsString(), User.class);
    }

    private void entaoEuDeveriaTerUmaListaDe5Usuarios() {
        assertEquals("Deveria existir 5 usuários cadastrados.", 5, users.size());
    }

    @SuppressWarnings("serial")
    private void quandoEuBuscoTodosOsUsuarios() throws Exception {
        MvcResult result = mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andReturn();
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        this.users = gson.fromJson(result.getResponse().getContentAsString(), listType);
    }

    private void dadoQueEuTenho5Usuarios() {
        userRepository.save(User.builder().id(1L).name("Ana").password("1234").build());
        userRepository.save(User.builder().id(2L).name("Maria").password("1234").build());
        userRepository.save(User.builder().id(3L).name("Joao").password("1234").build());
        userRepository.save(User.builder().id(4L).name("Mario").password("1234").build());
        userRepository.save(User.builder().id(5L).name("Joaquim").password("1234").build());
    }

    private String toJson(Object src) {
        return gson.toJson(src);
    }

}
