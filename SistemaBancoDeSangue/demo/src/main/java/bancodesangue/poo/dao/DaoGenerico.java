package bancodesangue.poo.dao;

import java.util.List;

public interface DaoGenerico<T> {
    T inserir(T entidade);

    T excluir(T entidade);

    T buscarPorId(Long id);

    List<T> buscarTodos();
}
