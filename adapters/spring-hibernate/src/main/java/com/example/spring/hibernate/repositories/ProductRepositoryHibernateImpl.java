package com.example.spring.hibernate.repositories;

import com.example.models.Product;
import com.example.port.ProductRepository;
import com.example.spring.hibernate.mapper.ProductMapper;
import com.example.spring.hibernate.models.ProductHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryHibernateImpl implements ProductRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product save(Product product) {
        Transaction transaction = null;
        ProductHibernate productHibernate = ProductMapper.asProductEntity(product);
        ProductHibernate savedProductHibernate = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            savedProductHibernate = (ProductHibernate) session.merge(productHibernate);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return ProductMapper.asProductDto(savedProductHibernate);
    }

    @Override
    public void delete(Product product) {
        Transaction transaction = null;
        ProductHibernate productHibernate = ProductMapper.asProductEntity(product);
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(productHibernate);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Product> findById(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductHibernate> query = builder.createQuery(ProductHibernate.class);
            Root<ProductHibernate> root = query.from(ProductHibernate.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Query<ProductHibernate> q=session.createQuery(query);
            ProductHibernate productHibernate=q.getSingleResult();
            return Optional.of(productHibernate).map(ProductMapper::asProductDto);
        } catch (NoResultException e) {
            // log error
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product product) {
        return save(product);
    }

    @Override
    public Optional<Product> findByLabel(String label) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductHibernate> query = builder.createQuery(ProductHibernate.class);
            Root<ProductHibernate> root = query.from(ProductHibernate.class);
            query.select(root).where(builder.equal(root.get("label"), label));
            Query<ProductHibernate> q=session.createQuery(query);
            ProductHibernate productHibernate=q.getSingleResult();
            return Optional.of(productHibernate).map(ProductMapper::asProductDto);
        } catch (NoResultException e) {
            // log error
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductHibernate> query = builder.createQuery(ProductHibernate.class);
            Root<ProductHibernate> root = query.from(ProductHibernate.class);
            query.select(root);
            Query<ProductHibernate> q=session.createQuery(query);
            return q.getResultList().stream().map(ProductMapper::asProductDto).collect(Collectors.toList());
        }
    }
}
