package com.example.spring.hibernate.repositories;

import com.example.models.Merchant;
import com.example.models.Product;
import com.example.port.MerchantRepository;
import com.example.spring.hibernate.mapper.MerchantMapper;
import com.example.spring.hibernate.mapper.ProductMapper;
import com.example.spring.hibernate.models.MerchantHibernate;
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
public class MerchantRepositoryHibernateImpl implements MerchantRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Merchant> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MerchantHibernate> query = builder.createQuery(MerchantHibernate.class);
            Root<MerchantHibernate> root = query.from(MerchantHibernate.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<MerchantHibernate> q=session.createQuery(query);
            MerchantHibernate merchantHibernate=q.getSingleResult();
            return Optional.of(merchantHibernate).map(MerchantMapper::asMerchantDto);
        } catch (NoResultException e) {
            // Error must be logged
        }
        return Optional.empty();
    }

    @Override
    public Optional<Merchant> findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MerchantHibernate> query = builder.createQuery(MerchantHibernate.class);
            Root<MerchantHibernate> root = query.from(MerchantHibernate.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Query<MerchantHibernate> q=session.createQuery(query);
            MerchantHibernate foundMerchantHibernate=q.getSingleResult();
            return Optional.of(foundMerchantHibernate).map(MerchantMapper::asMerchantDto);
        } catch (NoResultException e) {
            // Error must be logged
        }
        return Optional.empty();
    }

    @Override
    public Merchant save(Merchant merchant) {
        MerchantHibernate merchantHibernate = MerchantMapper.asMerchantEntity(merchant);
        return MerchantMapper.asMerchantDto(save(merchantHibernate));
    }

    private MerchantHibernate save(MerchantHibernate merchantHibernate) {
        Transaction transaction = null;
        MerchantHibernate savedMerchantHibernate = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            savedMerchantHibernate = (MerchantHibernate) session.merge(merchantHibernate);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return savedMerchantHibernate;
    }

    @Override
    public Merchant update(Merchant merchant) {
        return save(merchant);
    }

    @Override
    public Merchant associateProduct(Merchant merchant, Product product) {
        MerchantHibernate merchantHibernate = MerchantMapper.asMerchantEntity(merchant);
        ProductHibernate productHibernate = ProductMapper.asProductEntity(product);
        merchantHibernate.associateProduct(productHibernate);
        return MerchantMapper.asMerchantDto(save(merchantHibernate));
    }

    @Override
    public void delete(Merchant merchant) {
        Transaction transaction = null;
        MerchantHibernate merchantHibernate = MerchantMapper.asMerchantEntity(merchant);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(merchantHibernate);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Merchant> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MerchantHibernate> query = builder.createQuery(MerchantHibernate.class);
            Root<MerchantHibernate> root = query.from(MerchantHibernate.class);
            query.select(root);
            Query<MerchantHibernate> q=session.createQuery(query);
            return q.getResultList().stream().map(MerchantMapper::asMerchantDto).collect(Collectors.toList());
        }
    }
}
