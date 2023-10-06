package com.liferay.docs.guestbook.portlet.portlet;

import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.portlet.constants.GuestbookPortletKeys;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author prince
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Guestbook",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/guestbook/view.jsp",
		"javax.portlet.name=" + GuestbookPortletKeys.GUESTBOOK,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class GuestbookPortlet extends MVCPortlet {

	public void addEntry(ActionRequest request, ActionResponse response)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntryLocalService.class.getName(), request);
		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		long entryId = ParamUtil.getLong(request, "entryId");

		if (entryId > 0) {
			try {
				_guestbookEntryLocalService.updateGuestbookEntry(
					serviceContext.getUserId(), guestbookId, entryId, userName, email, message, serviceContext);

				SessionMessages.add(request, "entryAdded");

				response.setRenderParameter("guestbookId", String.valueOf(guestbookId));
			}
			catch (Exception e) {
				System.out.println(e);

				PortalUtil.copyRequestParameters(request, response);

				SessionErrors.add(request, e.getClass().getName());

				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}
		}
		else {
			try {
				_guestbookEntryLocalService.addGuestbookEntry(
					serviceContext.getUserId(), guestbookId, userName, email, message, serviceContext);

				response.setRenderParameter("guestbookId", String.valueOf(guestbookId));
			}
			catch (Exception e) {
				PortalUtil.copyRequestParameters(request, response);

				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}
		}
	}

	public void deleteEntry(ActionRequest request, ActionResponse response) {
		long entryId = ParamUtil.getLong(request, "entryId");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		try {
			response.setRenderParameter("guestbookId", String.valueOf(guestbookId));

			_guestbookEntryLocalService.deleteGuestbookEntry(entryId);

			SessionMessages.add(request, "entryDeleted");
		}
		catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());

			Logger.getLogger(GuestbookPortlet.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				GuestbookEntry.class.getName(), renderRequest);

			long groupId = serviceContext.getScopeGroupId();

			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");

			List<Guestbook> guestbooks = _guestbookLocalService.getGuestbooks(groupId);

			if (guestbooks.isEmpty()) {
				Guestbook guestbook = _guestbookLocalService.addGuestbook(serviceContext.getUserId(), "Main", serviceContext);

				guestbookId = guestbook.getGuestbookId();
			}

			if (guestbookId == 0) {
				guestbookId = guestbooks.get(
					0
				).getGuestbookId();
			}

			renderRequest.setAttribute("guestbookId", guestbookId);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private GuestbookEntryLocalService _guestbookEntryLocalService;

	@Reference
	private GuestbookLocalService _guestbookLocalService;

}