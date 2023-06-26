package cuong.run.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface CommonRepository<T, ID> extends JpaRepository<T, ID> {
	<T> List<T> findBy(Class<T> classType);
	<T> Page<T> findBy(Class<T> classType, Pageable pageable);
	
	
	
//	@Query("select e from #{#entityName} e")
//	<T> Collection<T> findByCollection(Class<T> type);
//	
//	@Query("select e from #{#entityName} e")
//	<T> Page<T> findByPage(Class<T> type, Pageable pageable);

	
	/*Các loại khóa
	 * LockModeType.READ: Chế độ khóa chỉ đọc.
		LockModeType.WRITE: Chế độ khóa ghi, cho phép chỉnh sửa dữ liệu.
		LockModeType.OPTIMISTIC: Chế độ khóa lạc quan, dùng cho khóa phiên bản (version locking).
		LockModeType.PESSIMISTIC_READ: Chế độ khóa lạc quan chỉ đọc.
		LockModeType.PESSIMISTIC_WRITE: Chế độ khóa lạc quan ghi.
	 * */
//	@Lock(LockModeType.READ)
//	//phải có @Modifying mới sử dụng void được
//	@Modifying
//	@Query("delete from #{#entityName} u where u.id = ?1")
//	void delete(Object id);

	// không nên sử dụng khi class mapped đến cái khác
//	@Query("select e from #{#entityName} e where e.existss = true")
//	List<T> findActiveEntities();

	
	
	
}
