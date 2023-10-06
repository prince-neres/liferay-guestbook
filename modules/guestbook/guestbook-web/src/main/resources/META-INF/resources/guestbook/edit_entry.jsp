<%@ include file="../init.jsp" %>

<%
	long entryId = ParamUtil.getLong(renderRequest, "entryId");
	GuestbookEntry entry = null;

	if (entryId > 0) entry = GuestbookEntryLocalServiceUtil.fetchGuestbookEntry(entryId);

	long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");
%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/guestbook/view.jsp" />
</portlet:renderURL>

<portlet:actionURL name="addEntry" var="addEntryURL" />

<aui:form action="<%= addEntryURL %>" name="<portlet:namespace />fm">
	<aui:model-context bean="<%= entry %>" model="<%= GuestbookEntry.class %>" />
	<aui:fieldset>
		<aui:input name="name" />
		<aui:input name="email" />
		<aui:input name="message" />
		<aui:input name="entryId" type="hidden" />
		<aui:input name="guestbookId" type="hidden" value="<%= entry == null ? guestbookId : entry.getGuestbookId() %>" />
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" />
		<aui:button onClick="<%= viewURL %>" value="cancel" />
	</aui:button-row>
</aui:form>