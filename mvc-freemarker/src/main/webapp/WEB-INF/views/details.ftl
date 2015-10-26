

	<c:forEach var="task" begin="0" items="${tasks}">
		<li>${task.name}, ${task.description}</li>
	</c:forEach>

