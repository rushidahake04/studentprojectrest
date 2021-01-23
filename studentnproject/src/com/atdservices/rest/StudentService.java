    package com.atdservices.rest;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.atdservices.db.StudentDbOperation;
import com.atdservices.pojo.Student;

@Path("/student")
public class StudentService {

	@GET
	@Path("/hello")
	@Produces ("text/plain")
	public String getit()
	{
		return "hello rest";
	}

	/*   
	 * 
	 */	
	@GET
	@Path("/list")  
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Student> getStudentlist()	{
		return StudentDbOperation.getStudents();
	}

	@GET
	@Path("/list/{studentnumber}")  
	@Produces({MediaType.APPLICATION_JSON})
	public Student getStudent(@PathParam("studentnumber") String studentNumber)	{
		return StudentDbOperation.getStudent(studentNumber);
	}
	
	@POST
	@Path("/add")  
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addStudent(JSONObject inputJsonObj)	{
		boolean result = false;
		Student student = getStudentFromBody(inputJsonObj);
		result = StudentDbOperation.addStudent(student);

		if(result) {
			return Response.status(201).build();
		} else {
			return Response.status(500).build();
		}	
	}

	@POST
	@Path("/update")  
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateStudent(JSONObject inputJsonObj)	{
		boolean result = false;
		Student student = getStudentFromBody(inputJsonObj);
		result = StudentDbOperation.updateStudent(student);

		if(result) {
			return Response.status(200).entity(student).build();
		} else {
			return Response.status(500).build();
		}	
	}

	@DELETE
	@Path("/delete/{studentnumber}")  
	@Consumes({MediaType.APPLICATION_JSON})
	public Response deleteStudent(@PathParam("studentnumber") String studentNumber)	{
		boolean result = false;
		result = StudentDbOperation.deleteStudent(studentNumber);

		if(result) {
			return Response.status(200).build();
		} else {
			return Response.status(500).build();
		}	
	}

	@GET
	@Path("/listxml")  
	@Produces({MediaType.APPLICATION_XML})
	public ArrayList<Student> getStudentlistXml()	{
		return StudentDbOperation.getStudents();
	}
	
	
	
	private static Student getStudentFromBody(JSONObject inputJsonObj) {
		Student student = null;
		try {
			/* {"dateOfBirth":"1998-08-04T00:00:00+05:30","dateOfJoining":"2020-02-01T00:00:00+05:30","studentName":"Rushikesh","studentNumber":"1"} */
			String studentNumber = inputJsonObj.getString("studentNumber");
			String studentName = inputJsonObj.getString("studentName");
			String dateOfBirth = inputJsonObj.getString("dateOfBirth");
			String dateOfJoining = inputJsonObj.getString("dateOfJoining");
			student = new Student(studentNumber, studentName, dateOfBirth, dateOfJoining);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return student;

	}
}

