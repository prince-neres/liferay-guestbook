<%@ include file="../init.jsp" %>

<%
  String mvcPath = ParamUtil.getString(request, "mvcPath");
  ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
  Guestbook guestbook = (Guestbook) row.getObject();
%>

<liferay-ui:icon-menu>
  <portlet:renderURL var="editURL">
    <portlet:param name="guestbookId" value="<%= String.valueOf(guestbook.getGuestbookId()) %>" />
    <portlet:param name="mvcPath" value="/guestbook_admin/edit_guestbook.jsp" />
  </portlet:renderURL>
  <liferay-ui:icon image="edit" message="Edit" url="<%= editURL.toString() %>" />
  <portlet:actionURL name="deleteGuestbook" var="deleteURL">
    <portlet:param name="guestbookId" value="<%= String.valueOf(guestbook.getGuestbookId()) %>" />
  </portlet:actionURL>
  <liferay-ui:icon-delete url="<%= deleteURL %>" />
</liferay-ui:icon-menu>