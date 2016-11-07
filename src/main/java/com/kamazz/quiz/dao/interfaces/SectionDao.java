package com.kamazz.quiz.dao.interfaces;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.entity.Section;

import java.sql.Connection;
import java.util.List;

public interface SectionDao {
    List<Section> getAllSection(Connection conn) throws DaoSystemException;
    Section getSectionById(int id,Connection conn) throws DaoSystemException, NoSuchEntityException;
}
