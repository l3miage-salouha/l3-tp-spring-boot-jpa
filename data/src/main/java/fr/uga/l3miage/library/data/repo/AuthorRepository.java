package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepository implements CRUDRepository<Long, Author> {

    private final EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author save(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public Author get(Long id) {
        return entityManager.find(Author.class, id);
    }


    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }

    /**
     * Renvoie tous les auteurs
     *
     * @return une liste d'auteurs trié par nom
     */
    @Override
    public List<Author> all() {
        // definir la requete jpql
        String jpql = "SELECT e FROM Author e ORDER BY a.name";
        // creer la requete
        TypedQuery<Author> query = this.entityManager.createQuery(jpql, Author.class);
        // extraire tout les éléments de la table et les avoir sous forme d'une liste d'entity
        List<Author> allAuthors = query.getResultList();
        return allAuthors;
    }

    /**13
     * Recherche un auteur par nom (ou partie du nom) de façon insensible  à la casse.
     *
     * @param namePart tout ou partie du nom de l'auteur
     * @return une liste d'auteurs trié par nom
     */
    public List<Author> searchByName(String namePart) {

        // definir la requete jpql
        String jpql = "SELECT a FROM Author a WHERE a.fullName LIKE CONCAT('%', :namePart, '%')";
        TypedQuery<Author> query = entityManager.createQuery(jpql,Author.class);
        return query.setParameter("namePart", namePart).getResultList();
    }

    /** 12
     * Recherche si l'auteur a au moins un livre co-écrit avec un autre auteur
     *
     * @return true si l'auteur partage
    */
    public boolean checkAuthorByIdHavingCoAuthoredBooks(long authorId) {
        // TODO

        return entityManager.createQuery(
                "SELECT COUNT(DISTINCT a2) FROM Book b1 JOIN b1.authors a1 JOIN b1.authors a2 WHERE a1.id = :authorId AND a2.id <> :authorId",
                Long.class)
                .setParameter("authorId", authorId)
                .getSingleResult() > 0;
    }
}
