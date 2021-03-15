package com.revature.chronicle.services;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service to handle business logic surrounding data access layer for tags
 */
@Service
public class TagService {
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    @Autowired
    private TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> findAll() {
        try {
            return tagRepo.findAll();
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return new ArrayList<Tag>();
        }

    }

    public Tag findById(int id) {
        try{
            return tagRepo.findById(id).get();
        }
        catch (Exception e){
            logger.warn(e.getMessage());
            return null;
        }
    }
    
    public List<Tag> findByTypeIn(Collection<String> tagNames) {
    	try {
            return tagRepo.findByTypeIn(tagNames);
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return new ArrayList<Tag>();
        }
    }

    public Tag findByValue(String value) {
        try{
            return tagRepo.findByValue(value);
        }
        catch (Exception e){
            logger.warn(e.getMessage());
            return null;
        }
    }

    public boolean save(Tag tag) {
        try {
            tagRepo.save(tag);
            return true;
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public boolean deleteByID(int tagID) {
        try {
            tagRepo.deleteById(tagID);
            return true;
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
