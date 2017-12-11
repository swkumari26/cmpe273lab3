package com.cmpe273.Dropbox;

import com.cmpe273.Dropbox.controller.UserController;
import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.entity.UserGroup;
import com.cmpe273.Dropbox.repository.ContentRepository;
import com.cmpe273.Dropbox.repository.GroupRepository;
import com.cmpe273.Dropbox.repository.UserRepository;
import com.cmpe273.Dropbox.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
public class DropboxApplicationTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContentRepository contentRepository;
    @Autowired
    GroupRepository groupRepository;

    @Test
    public void check_if_repository_is_not_empty() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
    }
    @Test
    public void signUp_a_User() {
        User user = userRepository.save(new User("user5fn","user5ln","user5@gmail.com","user5"));

        assertThat(user).hasFieldOrPropertyWithValue("firstName", "user5fn");
        assertThat(user).hasFieldOrPropertyWithValue("lastName", "user5ln");
    }
    @Test
    public void get_all_user_accounts() throws Exception{
        User user1 = userRepository.save(new User("user5fn","user5ln","user5@gmail.com","user5"));
        User user2 = userRepository.save(new User("user6fn","user6ln","user6@gmail.com","user6"));
        User user3 = userRepository.save(new User("user7fn","user7ln","user7@gmail.com","user7"));
        User user4 = userRepository.save(new User("user8fn","user8ln","user8@gmail.com","user8"));
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(6).contains(user1,user2,user3,user4);
    }
    @Test
    public void create_folder() {
        Content content = contentRepository.save(new Content("6","/6/testFolder","testFolder",6,true));
        assertThat(content).hasFieldOrPropertyWithValue("contentName", "testFolder");
    }
    @Test
    public void delete_folder() {
        Content content = contentRepository.save(new Content("6","/6/testFolder","testFolder",6,true));
        contentRepository.delete(content);
        Iterable<Content> content1 = contentRepository.findAll();
        System.out.println("content1 is"+content1.toString());
        assertThat(content1).hasSize(11);
    }
    @Test
    public void create_group() {
        UserGroup group = groupRepository.save(new UserGroup("cmpe273",6));
        assertThat(group).hasFieldOrPropertyWithValue("memberId", 6);
    }
    @Test
    public void add_member_to_a_group() {
        UserGroup member = groupRepository.save(new UserGroup("cmpe273",6));
        UserGroup member1 = groupRepository.save(new UserGroup("cmpe273",7));
        Iterable<UserGroup> group = groupRepository.findAll();
        assertThat(group).hasSize(4);
    }
    @Test
    public void list_user_Content() {
        Iterable<Content> content= contentRepository.findByIdIsIn(Arrays.asList(16, 31, 33, 35));
        assertThat(content).hasSize(4);
    }
    @Test
    public void get_user_groups() {
        List<String> group = groupRepository.findByMemberId(6);
        assertThat(group).contains("cmpe273");
    }
    @Test
    public void get_user_log_info() {
        User user = userRepository.findOne(405);
        assertThat(user).hasFieldOrPropertyWithValue("firstName", "user10fn");
        assertThat(user).hasFieldOrPropertyWithValue("lastName", "user10ln");
    }

}
