package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository implements CRUDRepository<Long, Book> {

    private final EntityManager entityManager;

    @Autowired
    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book save(Book author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public Book get(Long id) {
        return entityManager.find(Book.class, id);
    }


    @Override
    public void delete(Book author) {
        entityManager.remove(author);
    }

    /** 11
     * Renvoie tous les auteurs par ordre alphabétique
     * @return une liste de livres
     */
    public List<Book> all() {
        return entityManager.createNamedQuery("all-books", Book.class).getResultList();
    }

    /** 10
     * Trouve les livres dont le titre contient la chaine passée (non sensible à la casse)
     * @param titlePart tout ou partie du titre
     * @return une liste de livres
     */
    public List<Book> findByContainingTitle(String titlePart) {
        String titlePartLower = titlePart.toLowerCase();
        // TODO créer les named query
        return entityManager.createNamedQuery("find-books-by-title", Book.class)
                // TODO completer l'appel pour utiliser le paramètre de cette méthode
                .setParameter("pattern", "%titlePartLower%")
                .getResultList();
    }

    /** 9
     * Trouve les livres d'un auteur donnée dont le titre contient la chaine passée (non sensible à la casse)
     * @param authorId id de l'auteur
     * @param titlePart tout ou partie d'un titre de livré
     * @return une liste de livres
     */
    public List<Book> findByAuthorIdAndContainingTitle(Long authorId, String titlePart) {
        // TODO créer les named query
        String titlePartLower = titlePart.toLowerCase();
        // creation de la named query
        TypedQuery query = entityManager.createNamedQuery("find-books-by-author-and-title", Book.class);
                // TODO completer l'appel pour utiliser les paramètres de cette méthode
        // setter les deux parametres
        query.setParameter("authorId",authorId);
        query.setParameter("pattern", "%titlePartLower%");

        return query.getResultList();
    }

    /** 8
     * Recherche des livres dont le nom de l'auteur contient la chaine passée (non sensible à la casse)
     * @param namePart tout ou partie du nom
     * @return une liste de livres
     */
    public List<Book> findBooksByAuthorContainingName(String namePart) {
        String namePartLower = namePart.toLowerCase();
        // TODO créer les named query
        return entityManager.createNamedQuery("find-books-by-authors-name", Book.class)
                // TODO completer l'appel pour utiliser le paramètre de cette méthode
                .setParameter("pattern","%namePartLower%")
                .getResultList();
    }

    /** 7
     * Trouve des livres avec un nombre d'auteurs supérieur au compte donné
     * @param count le compte minimum d'auteurs
     * @return une liste de livres
     */
    public List<Book> findBooksHavingAuthorCountGreaterThan(int count) {
        // TODO créer les named query
        return entityManager.createNamedQuery("find-books-by-several-authors", Book.class)
                // TODO completer l'appel pour utiliser le paramètre de cette méthode
                .setParameter("count", count)
                .getResultList();
    }

}
