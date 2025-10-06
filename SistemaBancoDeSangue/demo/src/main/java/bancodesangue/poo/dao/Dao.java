package bancodesangue.poo.dao;

import java.util.List;

public interface Dao<T> {
    void inserir(T entidade);

    void excluir(T entidade);

    T buscarPorId(Long id);

    List<T> buscarTodos();
}
