package com.lucascarvalho.api.itau.pix.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import com.lucascarvalho.api.itau.pix.repository.ChaveRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
public class ChaveControllerTest {

    @Autowired
    private ChaveController chaveController;

    @MockBean
    private ChaveRepository chaveRepository;

    private MockMvc mockMvc;

    private UUID uuid = UUID.randomUUID();

    private Date date =new java.util.Date();

    ChaveModel CHAVE_1 = new ChaveModel(uuid, "cpf", "46486693800", "corrente", "1234", "12345678", "Primeiro", "Um", "F", date);
    ChaveModel CHAVE_2 = new ChaveModel(UUID.randomUUID(), "email", "lucsl@live.com", "poupança", "4567", "87654321", "Segundo", "Dois", "J", date);
    ChaveModel CHAVE_3 = new ChaveModel(UUID.randomUUID(), "celular", "+5511957523455", "corrente", "1234", "12345678", "Terceiro", "Tres", "F", date);

    @BeforeEach
    public void setup() {
        List<ChaveModel> chave = new ArrayList<>();
        ChaveModel chaveModel = new ChaveModel();
        chaveModel.setId(uuid);
        chaveModel.setNmTipoChave("email");
        chaveModel.setVlChave("lucsl@live.com");
        chaveModel.setNmTipoConta("corrente");
        chaveModel.setNuAgencia("4567");
        chaveModel.setNuConta("87654321");
        chaveModel.setNmNomeCorrentista("Segundo");
        chaveModel.setNmSobrenomeCorrentista("Teste");
        chaveModel.setNmTipoPessoa("F");
        chaveModel.setDtInclusao(date);
        chave.add(chaveModel);
        chave.add(CHAVE_3);

        Mockito.when(chaveRepository.findAll()).thenReturn(chave);
        Mockito.when(chaveRepository.findByNmTipoChaveIgnoreCase(chaveModel.getNmTipoChave())).thenReturn(chave);
        Mockito.when(chaveRepository.findByNmTipoChaveAndVlChave(chaveModel.getNmTipoChave(), chaveModel.getVlChave())).thenReturn(chave);
        Mockito.when(chaveRepository.findByNmNomeCorrentistaLikeIgnoreCase(chaveModel.getNmNomeCorrentista())).thenReturn(chave);
        Mockito.when(chaveRepository.findByNuAgenciaAndNuConta(chaveModel.getNuAgencia(), chaveModel.getNuConta())).thenReturn(chave);
        Mockito.when(chaveRepository.findByNmTipoChaveIgnoreCaseAndVlChaveAndNuContaAndNuAgencia(chaveModel.getNmTipoChave(), chaveModel.getVlChave(), chaveModel.getNuAgencia(), chaveModel.getNuConta())).thenReturn(chave);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.chaveController).build();
    }

    @Test
    public void deveRetornarSucesso_QuandoInserirUsuario() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(CHAVE_1);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/pix/chave/salvar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json, false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vlChave").value("46486693800"));
    }

    @Test
    public void deveRetornarSucesso_QuandoBuscarChave() throws Exception {

        this.mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/pix/chave/tipo-chave/{nmTipoChave}", "email")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nmTipoChave").value("email"));
    }

    @Test
    public void deveRetornarSucesso_QuandoConsultarconsultarByTipoChaveAndVlChave() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/pix/chave/tipo-valor-chave/{nmTipoChave}/{vlChave}", "email", "lucsl@live.com")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nmTipoChave").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vlChave").value("lucsl@live.com"));
    }

    @Test
    public void deveRetornarSucesso_QuandoConsultarByNmNomeCorrentista() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/pix/chave/nm-correntista/{nmCorrentista}", "Segundo")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nmNomeCorrentista").value("Segundo"));
    }

    @Test
    public void deveRetornarSucesso_QuandoConsultarByNmAgenciaNmConta() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/pix/chave/agencia-conta/{nuAgencia}/{nuConta}", "4567", "87654321")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuAgencia").value("4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuConta").value("87654321"));
    }

    @Test
    public void deveRetornarSucesso_QuandoConsultarByTipoChaveAndVlChaveAndNuContaAndNuAgencia() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/pix/chave/consulta-combinada")
                                .param("nmTipoChave", "email")
                                .param("vlChave", "lucsl@live.com")
                                .param("nuConta", "4567")
                                .param("nuAgencia", "87654321")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nmTipoChave").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vlChave").value("lucsl@live.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuConta").value("87654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuAgencia").value("4567"));
    }

    @Test
    public void deveRetornarSucesso_QuandoListarTodos() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/pix/chave/listar-todos")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nmTipoChave").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vlChave").value("lucsl@live.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuConta").value("87654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nuAgencia").value("4567"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nmTipoChave").value("celular"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].vlChave").value("+5511957523455"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nmTipoConta").value("corrente"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nmTipoPessoa").value("F"));
    }
}
