package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.ProposalService;
import org.aleks4ay.hotel.service.UserService;

import java.sql.*;
import java.time.LocalDate;

public class ProposalMapper implements ObjectMapper<Proposal> {
    @Override
    public Proposal extractFromResultSet(ResultSet rs) throws SQLException {
        Proposal proposal = new Proposal();
        proposal.setId(rs.getLong("id"));
        proposal.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        proposal.setArrival(rs.getDate("arrival").toLocalDate());
        proposal.setDeparture(rs.getDate("departure").toLocalDate());
        proposal.setGuests(rs.getInt("guests"));
        proposal.setCategory(Category.valueOf(rs.getString("category")));
        proposal.setStatus(Proposal.Status.valueOf(rs.getString("status")));
        User user = new User();
        user.setId(rs.getLong("user_id"));
        proposal.setUser(user);
        return proposal;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Proposal proposal) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(proposal.getRegistered()));
        statement.setDate(2, Date.valueOf(proposal.getArrival()));
        statement.setDate(3, Date.valueOf(proposal.getDeparture()));
        statement.setInt(4, proposal.getGuests());
        statement.setString(5, proposal.getCategory().getTitle());
        statement.setString(6, proposal.getStatus().toString());
        statement.setLong(7, proposal.getUser().getId());
    }

    public static void main(String[] args) {
        User user = new UserService().getByLogin("rt");
        Proposal proposal = new Proposal(LocalDate.of(2021, 8, 8), LocalDate.of(2021, 8, 10), 3, Category.STANDARD, user);
        System.out.println("proposal = " + proposal);
        new ProposalService().create(proposal);
    }
}
