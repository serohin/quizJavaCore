package com.kamazz.quiz.dao.interfaces;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.model.Theme;

import java.sql.Connection;
import java.util.List;

public interface ThemeDao {
    List<Theme> getThemesBySectionId(int sectionId, Connection conn) throws DaoSystemException, NoSuchEntityException;

    ;
}
