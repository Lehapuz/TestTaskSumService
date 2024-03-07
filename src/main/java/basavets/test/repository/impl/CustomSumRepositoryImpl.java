package basavets.test.repository.impl;

import basavets.test.entity.Data;
import basavets.test.repository.CustomSumRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomSumRepositoryImpl implements CustomSumRepository {

    private static final String NAME = "name";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Data data) {
        entityManager.persist(data);
    }

    @Override
    @Transactional
    public void delete(Data data) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Data> criteriaDelete = criteriaBuilder.createCriteriaDelete(Data.class);
        Root<Data> dataRoot = criteriaDelete.from(Data.class);
        criteriaDelete.where(criteriaBuilder.equal(dataRoot.get(NAME), data.getName()));
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public Optional<Data> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Data> criteriaQuery = criteriaBuilder.createQuery(Data.class);
        Root<Data> dataRoot = criteriaQuery.from(Data.class);
        criteriaQuery.select(dataRoot).where(criteriaBuilder.equal(dataRoot.get(NAME), name));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }
}
