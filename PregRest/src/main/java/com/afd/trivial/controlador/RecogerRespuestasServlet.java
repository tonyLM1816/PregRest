package com.afd.trivial.controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.metamodel.SetAttribute;

import com.afd.trivial.modelo.Fachada;
import com.afd.trivial.modelo.FachadaImpl;
import com.afd.trivial.modelo.Jugador;
import com.afd.trivial.modelo.Partida;
import com.afd.trivial.modelo.Respuesta;

/**
 * Servlet implementation class RecogerRespuestasServlet
 */
@WebServlet("/RecogerRespuestas")
public class RecogerRespuestasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fachada fachada = FachadaImpl.getInstance();
		Partida partida = (Partida)request.getSession().getServletContext().getAttribute("partida");
		Jugador jugador = (Jugador)request.getSession().getAttribute("jugador");
		System.out.println("RECOGER RESPUESTAS: " + jugador);
		int numPreguntas= partida.getListaPreguntas().size();
		ArrayList<Integer>listaRespuestas= new ArrayList<Integer>(numPreguntas);
			for (int i = 0; i < numPreguntas; i++) {
				int idPregunta = partida.getListaPreguntas().get(i).getIdPregunta();
				int respuesta=Integer.parseInt(request.getParameter("resp"+idPregunta));
				listaRespuestas.add(respuesta);
			}
			jugador = fachada.corregirPartida(listaRespuestas, partida, jugador);
			request.getSession().setAttribute("jugador", jugador);
			request.getSession().setAttribute("listaRespuestas", listaRespuestas);
			//El m�todo sendRedirect crea un request nueva. Eso implica:
			//	No se puede pasar informaci�n a la jsp a trav�s de la request
			//	La URL va a aparecer en el navegador del usuario
			response.sendRedirect("partidaAcabada.jsp");
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
