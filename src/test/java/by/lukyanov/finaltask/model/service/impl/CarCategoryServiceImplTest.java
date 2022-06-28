package by.lukyanov.finaltask.model.service.impl;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.exception.ServiceException;
import by.lukyanov.finaltask.model.dao.impl.CarCategoryDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class CarCategoryServiceImplTest {
    private static final String title = "Category";
    private static final long id = 2L;
    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_PAGE_NUMBER = 10;
    private CarCategory carCategory;

    @Mock
    private CarCategoryDaoImpl carCategoryDao;

    @InjectMocks
    private CarCategoryServiceImpl carCategoryService;

    @BeforeEach
    void setUp() {
        carCategory = new CarCategory(id, title);
        openMocks(this);
    }

    @Test
    void addCarCategoryShouldReturnTrue() throws DaoException, ServiceException {
        given(carCategoryDao.insert(any(CarCategory.class))).willReturn(true);
        assertTrue(carCategoryService.addCarCategory(title));
    }

    @Test
    void addCarCategoryShouldReturnFalse() throws DaoException, ServiceException {
        given(carCategoryDao.insert(any(CarCategory.class))).willReturn(false);
        assertFalse(carCategoryService.addCarCategory(title));
    }

    @Test
    void addCarCategoryShouldThrowException() throws DaoException {
        given(carCategoryDao.insert(any(CarCategory.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.addCarCategory(title));
    }

    @Test
    void findAllCarCategoriesShouldReturnFilled() throws DaoException, ServiceException {
        List<CarCategory> categoryList = List.of(carCategory);
        given(carCategoryDao.findAll(anyInt(), anyInt())).willReturn(List.of(carCategory));
        assertEquals(categoryList, carCategoryService.findAllCarCategories());
    }

    @Test
    void findAllCarCategoriesShouldReturnEmpty() throws DaoException, ServiceException {
        given(carCategoryDao.findAll(anyInt(), anyInt())).willReturn(new ArrayList<>());
        assertThat(carCategoryService.findAllCarCategories()).isEmpty();
    }

    @Test
    void findAllCarCategoriesShouldThrowException() throws DaoException{
        given(carCategoryDao.findAll(anyInt(), anyInt())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.findAllCarCategories());
    }

    @Test
    void findCarCategoryByIdShouldExists() throws DaoException, ServiceException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.findById(anyLong())).willReturn(Optional.of(carCategory));
        assertThat(carCategoryService.findCarCategoryById(id)).isPresent()
                .get()
                .isEqualTo(carCategory);
    }

    @Test
    void findCarCategoryByIdShouldNotExists() throws DaoException, ServiceException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.findById(anyLong())).willReturn(Optional.empty());
        assertThat(carCategoryService.findCarCategoryById(id)).isNotPresent();
    }

    @Test
    void findCarCategoryByIdShouldThrowException() throws DaoException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.findById(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.findCarCategoryById(id));
    }

    @Test
    void updateCarCategoryShouldReturnTrue() throws DaoException, ServiceException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.update(any(CarCategory.class))).willReturn(true);
        assertTrue(carCategoryService.updateCarCategory(id, title));
    }

    @Test
    void updateCarCategoryShouldReturnFalse() throws DaoException, ServiceException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.update(any(CarCategory.class))).willReturn(false);
        assertFalse(carCategoryService.updateCarCategory(id, title));
    }

    @Test
    void updateCarCategoryShouldThrowException() throws DaoException {
        String id = String.valueOf(carCategory.getId());
        given(carCategoryDao.update(any(CarCategory.class))).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.updateCarCategory(id, title));
    }

    @Test
    void deleteCarCategoryShouldReturnTrue() throws DaoException, ServiceException {
        given(carCategoryDao.delete(anyLong())).willReturn(true);
        assertTrue(carCategoryService.deleteCarCategory(String.valueOf(carCategory.getId())));
    }

    @Test
    void deleteCarCategoryShouldReturnFalse() throws DaoException, ServiceException {
        given(carCategoryDao.delete(anyLong())).willReturn(false);
        assertFalse(carCategoryService.deleteCarCategory(String.valueOf(carCategory.getId())));
    }

    @Test
    void deleteCarCategoryShouldThrownException() throws DaoException {
        given(carCategoryDao.delete(anyLong())).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.deleteCarCategory(String.valueOf(carCategory.getId())));
    }

    @Test
    void findCarCategoryByTitleShouldExists() throws DaoException, ServiceException {
        given(carCategoryDao.findCarCategoryByTitle(title)).willReturn(Optional.of(carCategory));
        assertThat(carCategoryService.findCarCategoryByTitle(title)).isPresent()
                .get()
                .isEqualTo(carCategory);
    }

    @Test
    void findCarCategoryByTitleShouldNotExists() throws DaoException, ServiceException {
        given(carCategoryDao.findCarCategoryByTitle(title)).willReturn(Optional.empty());
        assertThat(carCategoryService.findCarCategoryByTitle(title)).isNotPresent();
    }

    @Test
    void findCarCategoryByTitleShouldThrowException() throws DaoException {
        given(carCategoryDao.findCarCategoryByTitle(title)).willThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> carCategoryService.findCarCategoryByTitle(title));
    }
}