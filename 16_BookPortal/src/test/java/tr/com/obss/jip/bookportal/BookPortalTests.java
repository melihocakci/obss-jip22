package tr.com.obss.jip.bookportal;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.javafaker.Faker;

import io.jsonwebtoken.Jwt;
import tr.PasswordGenerator;



@SpringBootTest
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookPortalTests {

    @Autowired
    public WebApplicationContext context;
    public MockMvc mockMvc;

    

    protected class UserInfo
    {
        UserInfo( String name, String password, String email, String gender ) 
        {
            this.name = name;
            this.password = password;
            this.email = email;
            this.gender = gender;
        }
        public String name;
        public String password;
        public String email;
        public String gender;
    }

    protected class BookInfo
    {
        BookInfo(String name, List<String> authors) {
            this.name = name;
            this.authors = authors;
        }
        public String name;
        public List<String> authors;
    };

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
     }

  
    @Autowired
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    @Order(1) 
    public void loginAsAdmin() throws Exception
    {
        System.out.println("TEST CASE : loginAsAdmin");
        
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"admin\",\"password\": \"admin123\"}")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists());
        
    }

   
    
    @Test
    @Order(2)
    public void createThousandsRandomBook() throws Exception
    {
        System.out.println("TEST CASE : createThousandsRandomBook");

        List<BookInfo> bookTestData =  new LinkedList<>();

        // test data from open library api
        
        System.out.println("CREATING RANDOM BOOK TEST DATA.");
        for(int i = 1 ; i < 3 ; i++) {
            
            String content = getHTML("https://openlibrary.org/search.json?title=The&page="+i);
            JSONObject obj = new JSONObject(content);
    
            JSONArray array = obj.getJSONArray("docs");
            
            

            System.out.println(array.length());
            for(int j = 0 ; j < array.length() ; j++) {
                JSONObject obj_ = array.getJSONObject(j);
                
                if(obj_.has("author_name") && obj_.has("title_suggest")) {
                    
                    String title = obj_.getString("title_suggest");
                    JSONArray authors = obj_.getJSONArray("author_name");
                    
                    List<String> authorList = new LinkedList<>(); 
                    for(int k = 0 ; k < authors.length() ; k++) {
                        authorList.add(authors.getString(k));
                    }
                    bookTestData.add(new BookInfo(title, authorList));
                    // System.out.println(title + " " + authorList);
                }

            }
    
            assertNotEquals(bookTestData.size(), 0);
            
        }

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
        .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");
        
        for( BookInfo info : bookTestData) {

            if(info.authors.size() < 4 && info.name.length() < 255) {
                String authors = info.authors.toString().substring(1, info.authors.toString().length() - 1);
                String bookContent = "{\"name\":\""+info.name+"\", \"author\":\"" + authors + "\"}";
                System.out.println( bookContent );
    
                mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookContent)
                    .header("Authorization", "Bearer " + token))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            }
        }

        
    }


    
    @Test
    @Order(3)
    public void createHundredRandomUser() throws Exception
    {
        System.out.println("TEST CASE : createHundredRandomUser");

        List<UserInfo> userTestData = new LinkedList<>();
        System.out.println("CREATING RANDOM USER TEST DATA");
        for (int i = 0 ; i < 50 ; i++) {
            Faker faker = new Faker(); 
            String username = faker.superhero().prefix()+faker.name().firstName()+faker.address().buildingNumber();
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
            String password = passwordGenerator.generate(8);
            String email = username + "@hotmail.com";
            String gender = ( Math.round( Math.random() ) == 1 ) ? "female" : "male";
            userTestData.add(new UserInfo(username, password, email, gender));
        }
        for (UserInfo userInfo : userTestData) {
            String username = userInfo.name;
            String password = userInfo.password;
            String email = userInfo.email;
            String gender = userInfo.gender;
            System.out.println(username + " " + password + " " + email + " " + gender);
            if(username.length() < 50 && password.length() < 500 && email.length() < 50) {
                mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \""+ username +"\", \"password\": \"" + password + "\", \"email\": \"" + email+"\", \"gender\": \"" + gender + "\"}") 
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

            }
        }
    }

    // Yeni kullanıcının oluşturulması TEST CASE 1
    @Test
    @Order(4)
    public void createUserAPI() throws Exception 
    {
        System.out.println("TEST CASE : createUserAPI");

        mockMvc.perform(MockMvcRequestBuilders
        
                    .post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"username\": \"mellos\", \"password\": \"12345678\", \"email\": \"amogusss@amogus.com\", \"gender\": \"female\"}") 
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    @Order(5)
    public void loginUser() throws Exception 
    {
        System.out.println("TEST CASE : loginUser");

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"username\": \"mellos\", \"password\": \"12345678\"}") 
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(6)
    public void createConflict() throws Exception
    {
        System.out.println("TEST CASE : createUserAPI");

        mockMvc.perform(MockMvcRequestBuilders
        
                    .post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"username\": \"mellos\", \"password\": \"12345678\", \"email\": \"amogusss@amogus.com\", \"gender\": \"female\"}") 
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @Order(7)
    public void invalidLogin() throws Exception
    {
        System.out.println("TEST CASE : invalidLogin");
        boolean badCredentialException = false;
        try{
            mockMvc.perform(MockMvcRequestBuilders
        
                    .post("/api/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"username\": \"mellos\", \"password\": \"123456789\"}") 
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        } catch( Exception exc) {
            badCredentialException = true;
        }

        finally {
            assertEquals(true, badCredentialException);
        }
        
    }
    
    @Test
    @Order(8)
    public void getAllUserAPI1() throws Exception 
    {
        System.out.println("TEST CASE : getAllUserAPI1");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user")
                    .accept(MediaType.ALL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }

    @Test
    @Order(9)
    public void getAllUsersAPI2() throws Exception
    {
        System.out.println("TEST CASE : getAllUsersAPI2");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortField", "username")
                    .param("sortOrder", "ascend")
                    .accept(MediaType.ALL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(10)
    public void parseUsersByPageList() throws Exception
    {   
        System.out.println("TEST CASE : parseUsersByPageList");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user")
                    .param("page", "2")
                    .param("size", "10")
                    .accept(MediaType.ALL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(11)
    public void getAllBook() throws Exception
    {
        System.out.println("TEST CASE : getAllBook");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/book")
                    .param("page", "1")
                    .param("size", "10")
                    .accept(MediaType.ALL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(12)
    public void sortByFieldUser() throws Exception
    {
        System.out.println("TEST CASE : sortByFieldUser");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user")
                .param("page", "0")
                .param("size", "10")
                .param("sortField", "username")
                .param("sortOrder", "descend")
                .accept(MediaType.ALL))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Order(13)
    public void searchByUsername() throws Exception
    {
        System.out.println("TEST CASE : searchByUsername");

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
        
    }

    @Test
    @Order(14)
    public void updatePasswordByUser() throws Exception
    {
        System.out.println("TEST CASE : updatePasswordByUser");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"mellos\",\"password\": \"12345678\"}") 
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
            .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/12")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/15")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/0")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(15)
    public void addBookToReadListByAdmin() throws Exception
    {
        System.out.println("TEST CASE : addBookToReadListByAdmin");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
            .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/3")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/100") 
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(16)
    public void addBookToReadListByUser() throws Exception
    {
        System.out.println("TEST CASE : addBookToReadListByUser");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"mellos\",\"password\": \"12345678\"}") 
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
            .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/7")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/8")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/read/99") 
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(17)
    public void addBookToFavoriteListByUser() throws Exception
    {
        System.out.println("TEST CASE : addBookToFavouriteListByUser");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"mellos\",\"password\": \"12345678\"}") 
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
            .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/favorite/16")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/favorite/3")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/favorite/99") 
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(18)
    public void addBookToReadListTwice() throws Exception
    {

    }

    @Test
    @Order(19)
    public void addBookToFavouriteListTwice() throws Exception
    {

    }

    @Test
    @Order(20)
    public void deleteUserItselfByUser() throws Exception
    {

    }

    @Test
    @Order(21)
    public void updateBookByAdmin() throws Exception
    {

    }

    @Test
    @Order(22)
    public void removeUserByAdmin() throws Exception
    {

    }

    @Test
    @Order(23)
    public void takeUserProfilInformation() throws Exception
    {

    }

    @Test
    @Order(24)
    public void addProfileImageByUser() throws Exception
    {

    }

   
    @Test
    @Order(25)
    public void removeBookByAdmin() throws Exception
    {
        System.out.println("TEST CASE : deleteAllBookFlagCheck");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
        .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

       
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/book/5")
            .header("Authorization", "Bearer " + token));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/book/5")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());

    }



    @Test
    @Order(26)
    public void createNewBookUser() throws Exception
    {
        // //admin olarak giriş yapılır
        // MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        //         .post("/api/auth")
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
        //         .accept(MediaType.APPLICATION_JSON))
        //         .andDo(MockMvcResultHandlers.print())
        //         .andExpect(MockMvcResultMatchers.status().isOk())
        //         .andReturn();

        // String content = result.getResponse().getContentAsString();
        // System.out.println(content);
        // // yeni kitabın bilgilerini bulunduran json stringi oluşturulur.
        // // olulturulan json stringi /api/book adresine gönderilir.
    }
    


    // TEST CASE 22 Integrity of JWT token
    @Test
    @Order(27)
    public void getAuthToken() throws Exception{

        System.out.println("TEST CASE : getAuthToken");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        
        JSONObject obj = new JSONObject(content);
        String token = obj.getString("body");
        
        assertNotNull(token);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        // String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        

        JSONObject payloadJsonObject = new JSONObject(payload);
        assertEquals(1, payloadJsonObject.getInt("id") );
        assertEquals("admin", payloadJsonObject.getString("sub"));
        assertEquals(18000, payloadJsonObject.getInt("exp") - payloadJsonObject.getInt("iat")); // 5 hours
        

    }



    @Test
    @Order(28)
    public void getBooksAPI() throws Exception 
    {
        System.out.println("TEST CASE : getBooksAPI");

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/book")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
            // .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
    }
    
    
   
    @Test
    @Order(29)
    public void deleteAllBookFlagCheck() throws Exception {
        System.out.println("TEST CASE : deleteAllBookFlagCheck");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
        .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

        for( int i = 1 ; i < 190 ; i++) {
            
            mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/book/"+i)
            .header("Authorization", "Bearer " + token));
        }

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/book_portal",
                "postgres", "727");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(deleted) as deleted FROM books");
    
            rs.next();
            assertEquals(190, rs.getInt("deleted"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            conn.close();
        }
    }

    @Test
    @Order(30)
    public void deleteReadList() throws Exception
    {
        System.out.println("TEST CASE : deleteReadList");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/book_portal",
            "postgres", "727");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from read_list");
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conn.rollback();
            System.exit(0);

        } finally {
            conn.close();
        }

        
    
    }

    @Test
    @Order(31)
    public void deleteFavouriteList() throws Exception
    {
        System.out.println("TEST CASE : deleteFavouriteList");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/book_portal",
            "postgres", "727");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from favorite_list");
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conn.rollback();
            System.exit(0);

        } finally {
            conn.close();
        }
    
    }


    @Test
    @Order(32)
    public void deleteAllBookDatabase() throws Exception {

        System.out.println("TEST CASE : deleteAllBookDatabase");
        

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/book_portal",
            "postgres", "727");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from books");
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conn.rollback();
            System.exit(0);

        } finally {
            conn.close();
        }
    }

    @Test
    @Order(33)
    public void resetDatabaseSequenceBooksId() throws Exception
    {
        System.out.println("TEST CASE : resetDatabaseSequenceBooksId");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/book_portal",
                "postgres", "727");
            Statement stmt = conn.createStatement();
            stmt.execute("ALTER SEQUENCE books_id_seq RESTART WITH 1");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            conn.close();
        }
    }

    
    


    @Test
    @Order(34)
    public void deleteAllUserFlag() throws Exception
    {
        System.out.println("TEST CASE : deleteAllUserFlag");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"admin\",\"password\": \"admin123\"}") 
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
        .andReturn();

        String tokenContent = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenContent);
        String token = obj.getString("body");

        for( int i = 2 ; i <= 51 ; i++) {
            
            mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/user/"+i)
            .header("Authorization", "Bearer " + token));
        }

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/book_portal",
                "postgres", "727");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(deleted) as deleted FROM users");
    
            rs.next();
            assertEquals(52, rs.getInt("deleted")); // +1 fooMock Object
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            conn.close();
        }
       
    }

    @Test
    @Order(35)
    public void deleteAllUserDatabase() throws Exception {
        
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/book_portal",
            "postgres", "727");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from users where role_id = 1");
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conn.rollback();
            System.exit(0);

        } finally {
            conn.close();
        }
    }

    @Test
    @Order(36)
    public void deleteMockUserFromDatabase() throws Exception {
        
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/book_portal",
            "postgres", "727");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from users where username = 'mellos'");
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conn.rollback();
            System.exit(0);

        } finally {
            conn.close();
        }
    }

    @Test
    @Order(37)
    public void resetDatabaseSequenceUsersId() throws Exception
    {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/book_portal",
                "postgres", "727");
            Statement stmt = conn.createStatement();
            stmt.execute("ALTER SEQUENCE users_id_seq RESTART WITH 2");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            conn.close();
        }
    }

    

    // @AfterTestClass
    // public void finish() {
    //     // remove all data from dataset
    // }

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

