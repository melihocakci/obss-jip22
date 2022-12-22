package tr.com.obss.jip.bookportal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@WebAppConfiguration
class BookPortalTests {

    @Autowired
    public WebApplicationContext context;
    public MockMvc mockMvc;

    @Autowired
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
   
    
    
    @Test
    public void getBooksAPI() throws Exception 
    {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/book")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }
    
    @Test
    public void createUserAPI() throws Exception 
    {
        mockMvc.perform(MockMvcRequestBuilders
        
                    .post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"username\": \"mellos\", \"password\": \"12345678\", \"email\": \"amogusss@amogus.com\", \"gender\": \"female\"}") 
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isConflict());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }
    
    @Test
    public void getAllUserAPI() throws Exception 
    {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortField", "username")
                    .param("sortOrder", "descend")
                    .param("username", "m")
                    .accept(MediaType.ALL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }

    // @Test
    // public void getEmployeeByIdAPI() throws Exception 
    // {
    //     mockMvc.perform( MockMvcRequestBuilders
    //             .get("/api/book/{id}", 0)
    //             .accept(MediaType.APPLICATION_JSON))
    //         .andDo(MockMvcResultHandlers.print())
    //         .andExpect(MockMvcResultMatchers.status().isOk());
    //         // .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1));
    // }
    // @Test
    // public void testSayHi() throws Exception {
    //     this.mockMvc.perform(MockMvcRequestBuilders.get("/{id}").param("name", "Joe"))
    //                 .andExpect(MockMvcResultMatchers.status().isOk())
    //                 .andExpect(MockMvcResultMatchers.model().attribute("msg", "Hi there, Joe."))
    //                 .andExpect(MockMvcResultMatchers.view().name("hello-page"))
    //                 .andDo(MockMvcResultHandlers.print());

    // }
}

