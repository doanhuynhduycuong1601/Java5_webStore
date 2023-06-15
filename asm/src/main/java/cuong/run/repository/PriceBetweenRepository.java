package cuong.run.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cuong.run.model.PriceBetween;

public interface PriceBetweenRepository extends JpaRepository<PriceBetween, Integer> {

	<T> List<T> findBy(Class<T> classType);
}
