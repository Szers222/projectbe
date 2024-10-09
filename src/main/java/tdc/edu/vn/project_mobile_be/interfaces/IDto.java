package tdc.edu.vn.project_mobile_be.interfaces;

/**
 * Interface IDto
 * @param <T>
 * T is a type of entity
 */
public interface IDto<T> {

    /**
     * Convert IDto to Entity
     * @return T entity tương ứng IDto
     */
    public T toEntity();
    /**
     * Convert Entity to IDto
     * @param entity
     * @return T IDto tương ứng Entity
     */
    public void toDto(T entity);
}
