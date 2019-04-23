package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.EventModel;

@Repository
public interface EventRepository extends PagingAndSortingRepository<EventModel, Long> {

	List<EventModel> findByBetId(Long betId);

	final String QUERY = "select ev.id_evento, ev.evento_descricao, ev.data_evento, "
			+ "merc.descricao, mercs.id_mercado_selecao as merc_selec_id, mercs.descricao_selecao, mercs.status, count(*) as num_palpites "
			+ "from evento ev "
			+ "inner join mercado merc ON merc.id = ev.id_mercado_fk "
			+ "inner join mercado_selecao mercs On mercs.id = merc.id_mercado_selecao_fk "
			+ "WHERE mercs.status = 'REGISTERING' "
			+ "group by ev.id_evento, merc.id_mercado "
			+ "order by ev.data_evento, ev.id_evento";


	@Query(nativeQuery = true, value = QUERY)
	List<Object[]> listEventsGroupedByEventAndMarket();

}
