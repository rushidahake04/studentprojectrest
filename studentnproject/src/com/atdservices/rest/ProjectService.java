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
import com.atdservices.db.ProjectDbOperation;
import com.atdservices.pojo.Project;
@Path("/project")
public class ProjectService {
	
	/*   
	 * 
	 */	
	@GET
	@Path("/list")  
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Project> getProjectlist()	{
		return ProjectDbOperation.getProjects();
	}

	@GET
	@Path("/list/{projectnumber}")  
	@Produces({MediaType.APPLICATION_JSON})
	public Project getProject(@PathParam("projectnumber") String projectNumber)	{
		return ProjectDbOperation.getProject(projectNumber);
	}

	@POST
	@Path("/add")  
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addProject(JSONObject inputJsonObj)	{
		boolean result = false;
		Project project = getProjectFromBody(inputJsonObj);
		result = ProjectDbOperation.addProject(project);

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
	public Response updateProject(JSONObject inputJsonObj)	{
		boolean result = false;
		Project project = getProjectFromBody(inputJsonObj);
		result = ProjectDbOperation.updateProject(project);

		if(result) {
			return Response.status(200).entity(project).build();
		} else {
			return Response.status(500).build();
		}	
	}

	@DELETE
	@Path("/delete/{projectnumber}")  
	@Consumes({MediaType.APPLICATION_JSON})
	public Response deleteProject(@PathParam("projectnumber") String projectNumber)	{
		boolean result = false;
		result = ProjectDbOperation.deleteProject(projectNumber);

		if(result) {
			return Response.status(200).build();
		} else {
			return Response.status(500).build();
		}	
	}

	@GET
	@Path("/listxml")  
	@Produces({MediaType.APPLICATION_XML})
	public ArrayList<Project> getProjectlistXml()	{
		return ProjectDbOperation.getProjects();
	}



	private static Project getProjectFromBody(JSONObject inputJsonObj) {
		Project project = null;
		try {
			String projectNumber = inputJsonObj.getString("projectNumber");
			String projectName = inputJsonObj.getString("projectName");
			int projectDuration = inputJsonObj.getInt("projectDuration");
			String projectPlatform = inputJsonObj.getString("projectPlatform");
			project = new Project(projectNumber,projectName,projectDuration, projectPlatform);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return project;

	}
}
