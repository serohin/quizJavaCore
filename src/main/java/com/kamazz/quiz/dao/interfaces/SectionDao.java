package com.kamazz.quiz.dao.interfaces;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.model.Section;

import java.sql.Connection;
import java.util.List;

public interface SectionDao {
    List<Section> getAllSection(Connection conn) throws DaoSystemException;
}
