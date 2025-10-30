package bancodesangue.poo.dao;

import java.util.List;

public interface DaoGenerico<T> {
    T inserir(T entidade);

    T excluir(T entidade);

    T atualizar(T entidade);

    T buscarPorId(Long id);

    T buscarPorNome(String nome);

    List<T> buscarTodos();
}
