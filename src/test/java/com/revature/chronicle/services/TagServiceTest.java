package com.revature.chronicle.services;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepo repo;
    @InjectMocks
    private TagService service;

    @Test
    public void shouldReturnAListOfAllTags(){
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Technology","Java"));
        when(repo.findAll()).thenReturn(tags);
        List<Tag> result = service.findAll();
        Assertions.assertEquals(result, tags);
        verify(repo).findAll();
    }

    @Test
    public void shouldReturnATagById(){
        Tag tag = new Tag("Technology","Java");
        Optional<Tag> optional = Optional.of(tag);
        when(repo.findById(1)).thenReturn(optional);
        Tag result = service.findById(1);
        Assertions.assertEquals(result, tag);
        verify(repo).findById(1);
    }

    @Test
    public void shouldReturnNullIfNoTagFound(){
    	when(repo.findById(2)).thenReturn(Optional.empty());
        Optional<Tag> result = Optional.ofNullable(service.findById(2));
        Assertions.assertNotNull(result);
        verify(repo).findById(2);
    }

    @Test
    public void shouldSaveATagAndReturnTrue(){
        Tag tag = new Tag("Technology","Java");
        when(repo.save(tag)).thenReturn(tag);
        boolean result = service.save(tag);
        Assertions.assertTrue(result);
        verify(repo).save(tag);
    }

    @Test
    public void shouldFailToAddTagAndReturnFalse(){
        when(repo.save(null)).thenThrow(IllegalArgumentException.class);
        boolean result = service.save(null);
        Assertions.assertFalse(result);
        verify(repo).save(null);
    }
}

