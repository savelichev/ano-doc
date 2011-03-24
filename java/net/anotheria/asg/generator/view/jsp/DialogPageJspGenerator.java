package net.anotheria.asg.generator.view.jsp;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.asg.data.LockableObject;
import net.anotheria.asg.generator.GeneratedJSPFile;
import net.anotheria.asg.generator.GeneratorDataRegistry;
import net.anotheria.asg.generator.meta.MetaContainerProperty;
import net.anotheria.asg.generator.meta.MetaDocument;
import net.anotheria.asg.generator.meta.MetaEnumerationProperty;
import net.anotheria.asg.generator.meta.MetaProperty;
import net.anotheria.asg.generator.meta.StorageType;
import net.anotheria.asg.generator.view.CMSMappingsConfiguratorGenerator;
import net.anotheria.asg.generator.view.action.ModuleBeanGenerator;
import net.anotheria.asg.generator.view.meta.MetaDialog;
import net.anotheria.asg.generator.view.meta.MetaEmptyElement;
import net.anotheria.asg.generator.view.meta.MetaFieldElement;
import net.anotheria.asg.generator.view.meta.MetaFunctionElement;
import net.anotheria.asg.generator.view.meta.MetaListElement;
import net.anotheria.asg.generator.view.meta.MetaModuleSection;
import net.anotheria.asg.generator.view.meta.MetaSection;
import net.anotheria.asg.generator.view.meta.MetaView;
import net.anotheria.asg.generator.view.meta.MetaViewElement;
import net.anotheria.asg.generator.view.meta.MultilingualFieldElement;
import net.anotheria.util.StringUtils;

/**
 * Generates the jsps for the edit view.
 * 
 * @author another
 */
public class DialogPageJspGenerator extends AbstractJSPGenerator {
	
	/**
	 * Currently generated section.
	 */
	private MetaSection currentSection;
	/**
	 * Currently generated dialog.
	 */
	private MetaDialog currentDialog;
	
	public GeneratedJSPFile generate(MetaSection metaSection, MetaDialog dialog, MetaModuleSection section, MetaView view) {
		this.currentSection = metaSection;
		this.currentDialog = dialog;
		
		GeneratedJSPFile jsp = new GeneratedJSPFile();
		startNewJob(jsp);
		jsp.setName(getDialogName(dialog, section.getDocument()));
		jsp.setPackage(getContext().getJspPackageName(section.getModule()));

		resetIdent();
		currentDialog = dialog;

		append(getBaseJSPHeader());

		appendGenerationPoint("generateDialog");
		appendString("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"");
		appendString("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		appendString("<html:html>");
		increaseIdent();
		appendString("<head>");
		increaseIdent();
		appendString("<title>" + dialog.getTitle() + "</title>");
		generatePragmas(view);
		// appendString("<link href=\""+getCurrentCSSPath("admin.css")+"\" rel=\"stylesheet\" type=\"text/css\">");
		// *** CMS2.0 START ***

		appendString("<link rel=" + quote("stylesheet") + " type=" + quote("text/css") + " href=" + quote(getCurrentYUIPath("core/build/fonts/fonts-min.css")) + " />");
		appendString("<link rel=" + quote("stylesheet") + " type=" + quote("text/css") + " href=" + quote(getCurrentYUIPath("core/build/assets/skins/sam/skin.css")) + " />");
		appendString("<link rel=" + quote("stylesheet") + " type=" + quote("text/css") + " href=" + quote(getCurrentYUIPath("core/build/container/assets/skins/sam/container.css")) + " />");
		appendString("<link href=\"" + getCurrentCSSPath("newadmin.css") + "\" rel=\"stylesheet\" type=\"text/css\"/>");
		appendString("<link href=\"" + getCurrentCSSPath("fileuploader.css") + "\" rel=\"stylesheet\" type=\"text/css\"/>");

		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/yahoo-dom-event/yahoo-dom-event.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/container/container-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/menu/menu-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/element/element-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/button/button-min.js")) + "></script>");

		// appendString("<script type=" + quote("text/javascript") + " src=" +
		// quote(getCurrentYUIPath("core/build/animation/animation-min.js")) +
		// "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/datasource/datasource-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/autocomplete/autocomplete-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("core/build/dragdrop/dragdrop-min.js")) + "></script>");
		appendString("<script type=" + quote("text/javascript") + " src=" + quote(getCurrentYUIPath("anoweb/widget/ComboBox.js")) + "></script>");
		// *** CMS2.0 FINISH ***

		// *** CMS3.0 START ***
		appendString("<script type=\"text/javascript\" src=\"" + getCurrentJSPath("jquery-1.4.min.js") + "\"></script>");
		appendString("<script type=\"text/javascript\" src=\"" + getCurrentJSPath("anofunctions.js") + "\"></script>");
		appendString("<script type=\"text/javascript\" src=\"" + getCurrentJSPath("fileuploader.js") + "\"></script>");
		appendString("<script type=\"text/javascript\" src=\"" + getCurrentJSPath("tiny_mce/tiny_mce.js") + "\"></script>");
		// *** CMS3.0 FINISH ***

		decreaseIdent();
		appendString("</head>");
		appendString("<body>");
		appendString("<jsp:include page=\"" + getTopMenuPage() + "\" flush=\"true\"/>");
		appendString("<div class=\"right\">");
		appendString("<div class=\"r_w\">");
		increaseIdent();
		appendString("<div class=\"top_nav\">");
		increaseIdent();
		appendString("<div class=\"r_b_l\"><!-- --></div>");
		appendString("<div class=\"r_b_r\"><!-- --></div>");
		appendString("<div class=\"left_p\">");
		increaseIdent();
		appendString("<ul>");
		increaseIdent();
		appendString("<li class=\"first\">Scroll to:&nbsp;</li>");
		List<MetaViewElement> elements = createMultilingualList(dialog.getElements(), section.getDocument());
		for (int i = 0; i < elements.size(); i++) {
			MetaViewElement element = elements.get(i);
			while (elements.get(i) instanceof MultilingualFieldElement) {
				appendString("<logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument())) + " property="
						+ quote(ModuleBeanGenerator.FIELD_ML_DISABLED) + " value=" + quote("true") + ">");
				appendString("<li><a href=\"#" + element.getName() + "DEF" + "\">" + element.getName() + "DEF" + "</a></li>");
				appendString("</logic:equal>");
				appendString("<logic:notEqual name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument())) + " property="
						+ quote(ModuleBeanGenerator.FIELD_ML_DISABLED) + " value=" + quote("true") + ">");
				appendString("<li>");
				increaseIdent();
				String lang = getElementLanguage(element);
				appendString("<a href=\"#" + section.getDocument().getField(element.getName()).getName(lang) + "\">" + element.getName()
						+ "</a><a href=\"javascript:void(0);\" class=\"open_pop\">&nbsp;&nbsp;&nbsp;</a>");
				appendString("<div class=\"pop_up\">");
				increaseIdent();
				appendString("<div class=\"top\">");
				increaseIdent();
				appendString("<div><!-- --></div>");
				decreaseIdent();
				appendString("</div>");
				appendString("<div class=\"in_l\">");
				increaseIdent();
				appendString("<div class=\"in_r\">");
				increaseIdent();
				appendString("<div class=\"in_w\">");
				increaseIdent();
				appendString("<ul>");
				for (String sl : GeneratorDataRegistry.getInstance().getContext().getLanguages()) {
					appendString("<li class=\"lang_" + sl + " lang_hide\"><a href=\"#" + section.getDocument().getField(element.getName()).getName(sl) + "\">"
							+ sl + "</a></li>");
					i++;
					element = elements.get(i);
				}
				appendString("</ul>");
				decreaseIdent();
				appendString("</div>");
				decreaseIdent();
				appendString("</div>");
				decreaseIdent();
				appendString("</div>");
				appendString("<div class=\"bot\">");
				appendString("<div><!-- --></div>");
				appendString("</div>");
				decreaseIdent();
				appendString("</div>");
				decreaseIdent();
				appendString("</li>");
				appendString("</logic:notEqual>");
			}
			if (element instanceof MetaFieldElement) {
				appendString("<li><a href=\"#" + element.getName() + "\">" + element.getName() + "</a></li>");
			}
		}
		decreaseIdent();
		appendString("</ul>");
		appendString("<div class=\"clear\"><!-- --></div>");
		decreaseIdent();
		for (int i = 0; i < elements.size(); i++) {
			MetaViewElement element = elements.get(i);
			if (element instanceof MetaListElement)
				append(getElementEditor(section.getDocument(), element));

		}
		// SAVE AND CLOSE BUTTONS SHOULD BE HERE
		appendString("</div>");

		if (GeneratorDataRegistry.getInstance().getContext().areLanguagesSupported() && section.getDocument().isMultilingual()) {
			appendString("<div class=\"right_p\"><a href=\"#\"><img src=\"../cms_static/img/settings.gif\" alt=\"\"/></a>");
			increaseIdent();
			appendString("<div class=\"pop_up\">");
			increaseIdent();
			appendString("<div class=\"top\">");
			increaseIdent();
			appendString("<div><!-- --></div>");
			decreaseIdent();
			appendString("</div>");
			appendString("<div class=\"in_l\">");
			increaseIdent();
			appendString("<div class=\"in_r\">");
			increaseIdent();
			appendString("<div class=\"in_w\">");
			increaseIdent();
			// *** START MULILINGUAL COPY *** //
			int colspan = 2;
			addMultilanguageOperations(section, colspan);
			// *** END MULILINGUAL COPY *** //
			appendString("</div>");
			appendString("</div>");
			decreaseIdent();
			appendString("</div>");
			decreaseIdent();
			appendString("<div class=\"bot\">");
			appendIncreasedString("<div><!-- --></div>");
			appendString("</div>");
			decreaseIdent();
			appendString("</div>");
			decreaseIdent();
			appendString("</div>");
		}
		decreaseIdent();
		appendString("</div>");

		appendString("<div class=\"main_area\">");
		appendString("<div class=\"c_l\"><!-- --></div>");
		appendString("<div class=\"c_r\"><!-- --></div>");
		appendString("<div class=\"c_b_l\"><!-- --></div>");
		appendString("<div class=\"c_b_r\"><!-- --></div>");

		String entryName = quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument()));
		String result = "<logic:equal name=" + entryName + " property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("false") + "> \n";
		String path = CMSMappingsConfiguratorGenerator.getPath(((MetaModuleSection) metaSection).getDocument(), CMSMappingsConfiguratorGenerator.ACTION_LOCK);
		path += "?pId=<bean:write name=" + entryName + " property=\"id\"/>" + "&nextAction=showEdit";
		result += "<a href=\"#\" onClick= "
				+ quote("lightbox('All unsaved data will be lost!!!<br /> Really lock  "
						+ CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())
						+ " with id: <bean:write name=" + entryName + " property=\"id\"/>?','<ano:tslink>" + path + "</ano:tslink>');") + ">" + getLockImage()
				+ "&nbsp;Lock</a>";
		result += "</logic:equal>";
		appendString(result);

		if (StorageType.CMS.equals(((MetaModuleSection) metaSection).getDocument().getParentModule().getStorageType())) {
			appendString("<logic:equal name=" + entryName + " property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("true") + ">");

			path = CMSMappingsConfiguratorGenerator.getPath(((MetaModuleSection) metaSection).getDocument(), CMSMappingsConfiguratorGenerator.ACTION_UNLOCK);
			path += "?pId=<bean:write name=" + entryName + " property=\"id\"/>" + "&nextAction=showEdit";

			String alt = ((MetaModuleSection) metaSection).getDocument().getName() + " is locked by: <bean:write name=" + entryName + " property="
					+ quote(LockableObject.INT_LOCKER_ID_PROPERTY_NAME) + "/>, at: <bean:write name=" + entryName + " property="
					+ quote(LockableObject.INT_LOCKING_TIME_PROPERTY_NAME) + "/>";

			appendString("<a href=\"#\" onClick= "
					+ quote("lightbox('" + alt + "<br /> Unlock " + ((MetaModuleSection) metaSection).getDocument().getName()
							+ " with id: <bean:write name=" + entryName + " property=\"id\"/>?','<ano:tslink>" + path + "</ano:tslink>');") + ">"
					+ getUnLockImage(alt) + "" + " Unlock</a><span>&nbsp;Locked by <b><bean:write name="
					+ quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())) + " property="
					+ quote(LockableObject.INT_LOCKER_ID_PROPERTY_NAME) + "/></b>");
			appendString("at:  <b><bean:write name="
					+ quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())) + " property="
					+ quote(LockableObject.INT_LOCKING_TIME_PROPERTY_NAME) + "/></b></span>");
			appendString("</logic:equal>");
		}

		appendString("<form class=\"cmsDialog\" name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument()))
				+ " method=\"post\" action=" + quote(CMSMappingsConfiguratorGenerator.getPath(section.getDocument(), CMSMappingsConfiguratorGenerator.ACTION_UPDATE)) + ">");
		appendIncreasedString("<input type=" + quote("hidden") + " name=" + quote("_ts") + " value=" + quote("<%=System.currentTimeMillis()%>") + ">");
		appendIncreasedString("<input type=" + quote("hidden") + " name=" + quote(ModuleBeanGenerator.FLAG_FORM_SUBMITTED) + " value=" + quote("true") + ">");
		appendIncreasedString("<input type=" + quote("hidden") + " name=" + quote("nextAction") + " value=" + quote("close") + ">");

		appendString("<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">");
		appendString("<tbody>");
		increaseIdent();
		appendString("<bean:write name=\"description.null\" ignore=\"true\"/>");
		decreaseIdent();
		appendString("<tr>");
		increaseIdent();
		appendString("<td align=\"left\">");
		appendString("<div class=\"clear\"><!-- --></div>");
		// appendString("</logic:equal>");
		// UNLOCK HERE!!!!!
		appendString("</td>");
		decreaseIdent();
		appendString("</tr>");

		// *** CMS2.0 START ***

		List<MetaViewElement> richTextElementsRegistry = new ArrayList<MetaViewElement>();
		List<String> linkElementsRegistry = new ArrayList<String>();
		// *** CMS2.0 FINISH ***

		for (int i = 0; i < elements.size(); i++) {
			MetaViewElement element = elements.get(i);
			// *** CMS2.0 START ***
			if (element instanceof MetaListElement) {
				// now we draw control elements upside our page
				i++;
				continue;
			}
			if (element instanceof MetaFieldElement) {
				MetaDocument doc = ((MetaModuleSection) metaSection).getDocument();
				MetaProperty p = doc.getField(element.getName());
				if (element.isRich())
					if (p.getType() == MetaProperty.Type.TEXT)
						richTextElementsRegistry.add(element);

				if (p.isLinked())
					linkElementsRegistry.add(element.getName());
			}
			// *** CMS2.0 FINISH ***

			String lang = getElementLanguage(element);

			// ALTERNATIVE EDITOR FOR DISABLED MODE
			if (lang != null && lang.equals(GeneratorDataRegistry.getInstance().getContext().getDefaultLanguage())) {
				appendString("<logic:equal name="
						+ quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())) + " property="
						+ quote(ModuleBeanGenerator.FIELD_ML_DISABLED) + " value=" + quote("true") + ">");
				appendString("<td align=\"right\"> <a id=\"" + element.getName() + "DEF\" name=\"" + element.getName() + "DEF\"></a>");
				increaseIdent();
				String name = section.getDocument().getField(element.getName()).getName() + "<b>DEF</b>";
				if (name == null || name.length() == 0)
					name = "&nbsp;";
				appendString(name);
				decreaseIdent();
				appendString("</td>");
				appendString("<td align=\"left\">&nbsp;");
				append(getElementEditor(section.getDocument(), element));
				appendString("&nbsp;<i><bean:write name=\"description." + element.getName() + "\" ignore=\"true\"/></i>");
				appendString("</td>");
				appendString("</tr>");
				appendString("</logic:equal>");
			}// END ALTERNATIVE EDITOR FOR MULTILANG DISABLED FORM

			if (lang != null)
				appendString("<logic:equal name="
						+ quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())) + " property="
						+ quote(ModuleBeanGenerator.FIELD_ML_DISABLED) + " value=" + quote("false") + ">");

			// Language Filtering Settings
			String displayLanguageCheck = "";
			if (element instanceof MultilingualFieldElement) {
				MultilingualFieldElement multilangualElement = (MultilingualFieldElement) element;
				displayLanguageCheck = "<logic:equal name=\"display" + multilangualElement.getLanguage()
						+ "\" value=\"false\">style=\"display:none\"</logic:equal> class=\"lang_hide lang_" + multilangualElement.getLanguage() + "\"";
			}

			appendString("<tr class=\"cmsProperty\"" + displayLanguageCheck + ">");
			increaseIdent();
			increaseIdent();
			appendString("<td align=\"right\">");
			String name = lang == null ? element.getName() : section.getDocument().getField(element.getName()).getName(lang);
			if (name == null || name.length() == 0)
				name = "&nbsp;";
			appendString("<a id=\"" + name + "\" name=\"" + name + "\"></a>");
			appendString(name);
			decreaseIdent();
			if (element.isRich()) {
				appendString("<div class=\"clear\"></div>");
				appendString("<a href=\"javascript:;\" onmousedown=\"tinyMCE.get('" + section.getDocument().getField(element.getName()).getName(lang)
						+ "_ID').show();\" class=\"rich_on_off\" style=\"display:none;\">on</a>");
				appendString("<a href=\"javascript:;\" onmousedown=\"tinyMCE.get('" + section.getDocument().getField(element.getName()).getName(lang)
						+ "_ID').hide();\" class=\"rich_on_off\">off</a>");
				appendString("<span class=\"rich_on_off\">Rich:</span>");
			}
			appendString("</td>");
			appendString("<td align=\"left\">&nbsp;");
			increaseIdent();
			append(getElementEditor(section.getDocument(), element));
			appendString("&nbsp;<i><bean:write name=\"description." + element.getName() + "\" ignore=\"true\"/></i>");
			decreaseIdent();
			appendString("</td>");
			decreaseIdent();
			appendString("</tr>");

			if (lang != null)
				appendString("</logic:equal>");
		}
		appendString("<tr>");
		appendString("</tr>");
		appendString("<tr>");
		appendString("</tr>");
		appendString("<tr>");
		appendString("</tr>");
		appendString("<tr>");
		appendString("</tr>");
		decreaseIdent();
		appendString("</tbody>");
		decreaseIdent();
		appendString("</table>");
		appendString("</form>");
		appendString("<div class=\"clear\"><!-- --></div>");

		appendString("<div class=\"generated\"><span><bean:write name=" + quote("objectInfoString") + "/></span>");

		// Link to the Links to Me page
		appendString("<logic:present name=" + quote("linksToMe") + " scope=" + quote("request") + ">");
		String linksToMePagePath = CMSMappingsConfiguratorGenerator.getPath(section.getDocument(), CMSMappingsConfiguratorGenerator.ACTION_LINKS_TO_ME) + "?pId=<bean:write name="
				+ quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection) metaSection).getDocument())) + " property=\"id\"/>";
		appendString("<a href=" + quote("<ano:tslink>" + linksToMePagePath + "</ano:tslink>") + ">Show direct links to  this document</a>");
		appendString("</logic:present>");
		appendString("<div class=\"clear\"><!-- --></div>");
		appendString("</div>");
		appendString("</div>");
		appendString("</div>");
		appendString("<div class=\"lightbox\" style=\"display:none;\">");
		appendString("<div class=\"black_bg\"><!-- --></div>");
		appendString("<div class=\"box\">");
		increaseIdent();
		appendString("<div class=\"box_top\">");
		increaseIdent();
		appendString("<div><!-- --></div>");
		appendString("<span><!-- --></span>");
		appendString("<a class=\"close_box\"><!-- --></a>");
		appendString("<div class=\"clear\"><!-- --></div>");
		decreaseIdent();
		appendString("</div>");
		appendString("<div class=\"box_in\">");
		increaseIdent();
		appendString("<div class=\"right\">");
		increaseIdent();
		appendString("<div class=\"text_here\">");
		appendString("</div>");
		decreaseIdent();
		appendString("</div>");
		decreaseIdent();
		appendString("</div>");
		appendString("<div class=\"box_bot\">");
		increaseIdent();
		appendString("<div><!-- --></div>");
		appendString("<span><!-- --></span>");
		decreaseIdent();
		appendString("</div>");
		decreaseIdent();
		appendString("</div>");
		appendString("</div>");
		appendString("</body>");
		decreaseIdent();

		generateRichTextEditorJS(section.getDocument(), richTextElementsRegistry);
		generateLinkElementEditorJS(section.getDocument(), linkElementsRegistry);

		decreaseIdent();
		appendString("</html:html>");
		appendString("<!-- / generated by JspMafViewGenerator.generateDialog -->");

		return jsp;
	}
	
	private String getElementEditor(MetaDocument doc, MetaViewElement element){
		if (element instanceof MetaEmptyElement)
			return "&nbsp;";
		if (element instanceof MetaFieldElement)
			return getFieldEditor((MetaFieldElement)element);
		if (element instanceof MetaListElement)
			return getListEditor(doc, (MetaListElement)element);
		if (element instanceof MetaFunctionElement)
			return getFunctionEditor(doc, (MetaFunctionElement)element);

		return "";
			
	}
	
	private String getListEditor(MetaDocument doc, MetaListElement element){
		String ret = "";
		
		List<MetaViewElement> elements = element.getElements();
		for (int i=0; i<elements.size(); i++){
			ret += getElementEditor(doc, elements.get(i));
			if (i<elements.size()-1)
				ret += "&nbsp;";
		}
			
		
		return ret;
	}
	
	
	private String getLinkEditor(MetaFieldElement element, MetaProperty p){
		//for now we have only one link...
		String ret = "";
		String lang = getElementLanguage(element); 
		
		/* CMS1.0
		ret += "<html:select size=\"1\" property="+quote(p.getName(lang))+">";
		ret += "<html:optionsCollection property="+quote(p.getName()+"Collection"+(lang==null ? "":lang))+" filter=\"false\"/>";
		ret += "</html:select>";
		ret += "&nbsp;";
		ret += "(<i>old:</i>&nbsp;<bean:write property="+quote(p.getName()+"CurrentValue"+(lang==null ? "":lang))+" name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" filter="+quote("false")+"/>)";
		 */
		
		//*** CMS2.0 START ***
		ret += "<em id="+quote(StringUtils.capitalize(p.getName()))+" name="+quote(p.getName())+" class=\"selectBox\"></em><div id=\""+StringUtils.capitalize(p.getName(lang))+"Selector\"></div>";
		ret += " (<i>old:</i>&nbsp;<bean:write property="+quote(p.getName()+"CurrentValue")+" name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" filter="+quote("false")+"/>)";			
		//*** CMS2.0 FINISH ***
		
		return ret;
	}
	
	private String getEnumerationEditor(MetaFieldElement element, MetaProperty p){
		String ret = "";
		String lang = getElementLanguage(element); 
		
		ret += "<select name=\""+p.getName(lang)+"\">";
		ret += "<logic:iterate indexId=\"index\" id=\"element\" property=\""+ p.getName() +"Collection\" name=\""+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument())+ "\">";
		ret += "<option value=\"<bean:write name=\"element\" property=\"value\"/>\" <logic:equal name=\""+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument())+ "\" property=\""+p.getName()+"CurrentValue"+(lang==null ? "":lang)+"\" value=\"${element.label}\">selected</logic:equal>><bean:write name=\"element\" property=\"label\"/></option>";
		ret += "</logic:iterate>";
		ret += "</select>";
		
		
		ret += "&nbsp;";
		ret += "(<i>old:</i>&nbsp;<bean:write property="+quote(p.getName()+"CurrentValue"+(lang==null ? "":lang))+" name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" filter="+quote("false")+"/>)";
		
		return ret;
	}
	
	private String getFieldEditor(MetaFieldElement element){
		MetaDocument doc = ((MetaModuleSection)currentSection).getDocument();
		MetaProperty p = doc.getField(element.getName());
		
		if (p.isLinked())
			return getLinkEditor(element, p);
			
		if (p instanceof MetaEnumerationProperty){
			return getEnumerationEditor(element, p);
		}
		
		if (p instanceof MetaContainerProperty) {
			return getContainerLinkEditor(element, (MetaContainerProperty)p);
		}
		

		switch (p.getType()) {
		case STRING:
			return getStringEditor(element, p);
		case TEXT:
			return getTextEditor(element, p);
		case LONG:
			return getStringEditor(element, p);
		case INT:
			return getStringEditor(element, p);
		case DOUBLE:
			return getStringEditor(element, p);
		case FLOAT:
			return getStringEditor(element, p);
		case BOOLEAN:
			return getBooleanEditor(element, p);
		case IMAGE:
			return getImageEditor(element, p);
		default:
			return p.getType().getName();
		}
		
	}
	
	private String getContainerLinkEditor(MetaFieldElement element, MetaContainerProperty p){
		String ret = "";
		String lang = getElementLanguage(element); 
		String name = quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()));
		ret += "<logic:equal name="+name+" property="+quote("id")+" value="+quote("")+">";
		ret += "none";
		ret += "</logic:equal>";
		ret += "<logic:notEqual name="+name+" property="+quote("id")+" value="+quote("")+">";
		ret += "<bean:write name="+name+" property="+quote(p.getName(lang))+"/>";
		ret += "&nbsp;";
		ret += "element";
		ret += "<logic:notEqual name="+name+" property="+quote(p.getName(lang))+" value="+quote("1")+">";
		ret += "s";
		ret += "</logic:notEqual>";
		ret += "&nbsp;";
		String actionName = CMSMappingsConfiguratorGenerator.getContainerPath(((MetaModuleSection)currentSection).getDocument(), p, CMSMappingsConfiguratorGenerator.ACTION_SHOW);
		actionName += "?ownerId=<bean:write name="+name+" property="+quote("id")+"/>";
		ret += "<a href="+quote(actionName)+">&nbsp;&raquo&nbsp;Edit&nbsp;</a>";
		ret += "</logic:notEqual>";
		
		return ret;
	}
	
	

	private String getImageEditor(MetaFieldElement element, MetaProperty p){
		String beanName = CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument());
		String propertyWriter = "<bean:write name="+quote(beanName)+" property="+quote(p.getName()) + "/>";
		String ret ="";
		ret += "<logic:present name="+quote(beanName)+" property="+quote(p.getName()) + ">\r";
		ret += "<a target=\"_blank\" href=\"getFile?pName=" + propertyWriter + "\"><img class=\"thumbnail\" alt=" + quote(propertyWriter) + " src=\"getFile?pName=" + propertyWriter + "\"></a>\r";
		ret += "</logic:present>\r";
		ret += "&nbsp;<i><bean:write name=\"description." + p.getName() + "\" ignore=\"true\"/></i>\r";
		
		ret += "<div id=\"file-uploader-" + p.getName() + "\" class=\"image_uploader\"><!-- --></div>\r";
		ret += "<script>\r";
		ret += "$(document).ready(function() {\r";
		ret += "	var uploader = new qq.FileUploader({\r";
		ret += "	    element: document.getElementById('file-uploader-" + p.getName() +"'),\r";
		ret += "	    action: '${pageContext.request.contextPath}/cms/fileUpload',\r";
		ret += "	    params: {\r";
		ret += "	    	property: '" + p.getName() + "'\r";
	    ret += "	    }\r";

		ret += "	});\r";
		ret += "});\r";
		ret += "</script>\r";
		return ret;
	}

	private String getStringEditor(MetaFieldElement element, MetaProperty p){
		return getInputEditor(element, p, "text");
	}
	
	private String getBooleanEditor(MetaFieldElement element, MetaProperty p){
		return getInputEditor(element, p, "checkbox");
	}
	
	private String getInputEditor(MetaFieldElement element, MetaProperty p, String inputType){
		String ret ="";
		String lang = getElementLanguage(element); 
		
		ret += "<input type=" + quote(inputType) + " name="+quote(p.getName(lang));
		
		//ret += "<html:text filter=\"false\" property="+quote(element.getName());
		if (inputType.equalsIgnoreCase("checkbox"))	{
			ret += " <logic:equal name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" property="+quote(p.getName(lang))+" value=\"true\""; 
			ret += ">";
			ret += "checked</logic:equal>";
		}
		else {
			ret += " value=\"<bean:write name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" property="+quote(p.getName(lang)) + "/>\"";
		}
		if (element.isReadonly())
			ret += " readonly="+quote("true");
		ret += "/>";

		if (element.isReadonly())
			ret += "&nbsp;<i>readonly</i>";
		
		return ret;
	}

	private String getTextEditor(MetaFieldElement element, MetaProperty p){
		String lang = getElementLanguage(element);
		String ret ="";
		
		ret += "<textarea cols=\"\" rows=\"16\" id="+quote(p.getName(lang) + "_ID")+" name="+quote(p.getName(lang));
		ret += ">";
		ret += "<bean:write filter=\"false\" name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument()))+" property="+quote(p.getName(lang))+" />";
		ret += "</textarea>";

		return ret;
	}
	
	private void generateLinkElementEditorJS(MetaDocument doc, List<String> linkElements){
		appendString("<script type=\"text/javascript\">");
		increaseIdent();
		for(String elName: linkElements){

			//FIXME: here is assumed that links can't be multilanguage
			String elCapitalName = StringUtils.capitalize(elName);
			String beanName = CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, ((MetaModuleSection)currentSection).getDocument());
			
			appendString("//Initializing items for " + elName);
			appendString("var " +elName+ "Json = {items:[");
			appendString("<logic:iterate id=\"item\" name="+quote(beanName)+" property=\""+elName+"Collection\" type=\"net.anotheria.webutils.bean.LabelValueBean\">");
			increaseIdent();
			appendString("{id:\"<bean:write name=\"item\" property=\"value\" filter=\"true\"/>\",name:\"<bean:write name=\"item\" property=\"label\" filter=\"true\"/>\"},");
			//appendString("{id:\"${item.value}\",name:\"${item.label}\"},");
			decreaseIdent();
			appendString("</logic:iterate>");
			appendString("]};");
			appendString("var selection"+elCapitalName+"Json = {");
			increaseIdent();
			appendString("id:'<bean:write name="+quote(beanName)+" property="+quote(elName)+"/>',name:'<bean:write name="+quote(beanName)+" property="+quote(elName + "CurrentValue")+"/>'");
			decreaseIdent();
			appendString("};");
			appendString("new YAHOO.anoweb.widget.ComboBox("+quote(elCapitalName)+",\""+elCapitalName+"Selector\","+elName+"Json,selection"+elCapitalName+"Json);");
		}
		decreaseIdent();
		appendString("</script>");
	}
	
	private void generateRichTextEditorJS(MetaDocument doc, List<MetaViewElement> richTextElements){
		
		appendString("<!-- TinyMCE -->");
		
		appendString("<script type=\"text/javascript\">");
		appendString("tinyMCE.init({");
		appendString("mode : \"exact\",");
		String allRichTextElements = "elements:\"";
		for (int i=0; i<richTextElements.size(); i++){
			String language = getElementLanguage(richTextElements.get(i));
			if (language == null) language = "";//allow tinyMCE for single language fields 
			allRichTextElements+=richTextElements.get(i).getName()+language+"_ID";
			if (i+1!=richTextElements.size())
				allRichTextElements+=", ";
		}
		appendString(allRichTextElements+"\",");
		appendString("theme : \"advanced\",");
		appendString("plugins : \"save, table\",");
		appendString("theme_advanced_layout_manager : \"SimpleLayout\",");
		appendString("theme_advanced_toolbar_align : \"left\",");
		appendString("theme_advanced_toolbar_location : \"top\",");
		appendString("theme_advanced_buttons1 : \"undo, redo, separator, bold, italic, underline, separator, justifyleft, justifycenter, justifyright, justifyfull, formatselect,  fontselect, fontsizeselect, forecolor\",");
		appendString("theme_advanced_buttons2 : \"bullist, numlist, separator, image, link, unlink, separator, table, code\",");
		appendString("theme_advanced_buttons3 : \"\",");
		appendString("theme_advanced_resize_horizontal : true");
		appendString("});");
		appendString("</script>");
		appendString("<!-- /TinyMCE -->");
		
	}
	
	private String getFunctionEditor(MetaDocument doc, MetaFunctionElement element){
		if (element.getName().equals("cancel")) {
			String onClick = "return confirm('All unsaved data will be lost!!!. Document will be unlocked"; 
			String cancel = CMSMappingsConfiguratorGenerator.getPath(((MetaModuleSection) currentSection).getDocument(), CMSMappingsConfiguratorGenerator.ACTION_CLOSE);
			cancel += "?pId=<bean:write name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) + " property=\"id\"/>";
			return "<a href=" + cancel + " class=\"button\" onClick="+onClick+"><span>Close</span></a>";
		}
		if (element.getName().equals("update")) {
			return getUpdateAndCloseFunction(doc, element);
		}

		if (element.getName().equals("updateAndStay")) {
			return getUpdateAndStayFunction(doc, element);
		}
		if (element.getName().equals("updateAndClose")) {
			return getUpdateAndCloseFunction(doc, element);
		}

		if (element.getName().equals("lock") && StorageType.CMS.equals(doc.getParentModule().getStorageType())) {
			//For now we dont draw Lock and Unlock functions here
			//return getLockFunctionLink(doc, element);
		}

		if (element.getName().equals("unlock") && StorageType.CMS.equals(doc.getParentModule().getStorageType())) {
			//For now we dont draw Lock and Unlock functions here
			//return getUnLockFunctionLink(doc, element);
		}


		return "";
	}
	
    private String getUpdateAndStayFunction(MetaDocument doc, MetaFunctionElement element){
		if(StorageType.CMS.equals(doc.getParentModule().getStorageType())){
			//creating logic for hiding or showing current operation link in Locking CASE!!!!!
			String result = "<logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) +
					" property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("true") + "> \n";
			result+="  <logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) +
					" property=" + quote(LockableObject.INT_LOCKER_ID_PROPERTY_NAME) + " value=" + quote("<%=(java.lang.String)session.getAttribute(\\"+quote("currentUserId\\")+")%>") + "> \n";
			result+="\t<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
					".nextAction.value='stay'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"apply.label.prefix\"/></span></a> \n";
			result+="  </logic:equal> \n";
			result+="</logic:equal> \n";
			result+="<logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) + " property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("false") + "> \n";
			result+="\t<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
					".nextAction.value='stay'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"apply.label.prefix\"/></span></a>\n";
			result+="</logic:equal> \n";
			return result;
		}
		return "<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
				".nextAction.value='stay'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"apply.label.prefix\"/></span></a>";
	}
	private String getUpdateAndCloseFunction(MetaDocument doc, MetaFunctionElement element){
		if(StorageType.CMS.equals(doc.getParentModule().getStorageType())){
			//creating logic for hiding or showing current operation link in Locking CASE!!!!!
			String result = "<logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) +
					" property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("true") + "> \n";
			result+="  <logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) +
					" property=" + quote(LockableObject.INT_LOCKER_ID_PROPERTY_NAME) + " value=" + quote("<%=(java.lang.String)session.getAttribute(\\"+quote("currentUserId\\")+")%>") + "> \n";
			result+="\t<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
					".nextAction.value='close'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"save.label.prefix\"/></span></a> \n";
			result+="  </logic:equal> \n";
			result+="</logic:equal> \n";
			result+="<logic:equal name=" + quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)) + " property=" + quote(LockableObject.INT_LOCK_PROPERTY_NAME) + " value=" + quote("false") + "> \n";
			result+="\t<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
					".nextAction.value='close'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"save.label.prefix\"/></span></a> \n";
			result+="</logic:equal> \n";
			return result;
		}
		return "<a href=\"#\" class=\"button\" onClick=\"document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+
				".nextAction.value='close'; document."+CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, doc)+".submit(); return false\"><span><bean:write name=\"save.label.prefix\"/></span></a>";
	}	

	/**
	 * Creating entries in JSP for Multilanguage Support!!!
	 * @param section
	 * @param colspan
	 */
	private void addMultilanguageOperations(MetaModuleSection section, int colspan) {
		appendString("<logic:equal name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+" value="+quote("false")+">");
		increaseIdent();
		appendString("<logic:notEqual name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote("id")+" value="+quote("")+">");
		increaseIdent();
		appendString("<form name=\"CopyLang\" id=\"CopyLang\" method=\"get\" action=\""+CMSMappingsConfiguratorGenerator.getPath(section.getDocument(), CMSMappingsConfiguratorGenerator.ACTION_COPY_LANG)+"\">");
		increaseIdent();
		appendString("<input type=\"hidden\" name=\"ts\" value=\"<%=System.currentTimeMillis()%>\"/><input type=\"hidden\" name=\"pId\" value=\"<bean:write name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote("id")+"/>\"/>");
		appendString("<div>");
		increaseIdent();
		appendString("Copy <select name=\"pSrcLang\">");
		increaseIdent();
		for (String sl : GeneratorDataRegistry.getInstance().getContext().getLanguages()){
			appendString("<option value=\""+sl+"\">"+sl+"</option>");
		}
		decreaseIdent();
		appendString("</select>");


		appendString("to");
		appendString("<select name=\"pDestLang\">");
		increaseIdent();
		for (String sl : GeneratorDataRegistry.getInstance().getContext().getLanguages()){
			appendString("<option value=\""+sl+"\">"+sl+"</option>");
		}
		decreaseIdent();
		appendString("</select>");
		decreaseIdent();
		appendString("</div>");
		appendString("<a href=\"#\" class=\"button\" onclick=\"document.CopyLang.submit(); return false\"><span>Copy</span></a>");
		
		decreaseIdent();
		appendString("</form>");
		appendString("<form name="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+" id="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+"  method=\"get\" action=\""+CMSMappingsConfiguratorGenerator.getPath(section.getDocument(), CMSMappingsConfiguratorGenerator.ACTION_SWITCH_MULTILANGUAGE_INSTANCE)+"\">");
		increaseIdent();
		appendString("<div>");
		appendString("<input type=\"hidden\" name=\"value\" value=\"true\"/><input type=\"hidden\" name=\"ts\" value=\"<%=System.currentTimeMillis()%>\"/><input type=\"hidden\" name=\"pId\" value=\"<bean:write name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote("id")+"/>\"/>");
		decreaseIdent();
		appendString("</div>");
		appendString("<a href=\"#\" class=\"button\" onclick=\"document."+ModuleBeanGenerator.FIELD_ML_DISABLED+".submit(); return false\"><span>Disable languages</span></a>");
		appendString("</form>");
		decreaseIdent();
		appendString("</logic:notEqual>");
		decreaseIdent();
		appendString("</logic:equal>");
		appendString("<logic:equal name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+" value="+quote("true")+">");
		increaseIdent();
		appendString("<div>");
		appendString("<form name="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+" id="+quote(ModuleBeanGenerator.FIELD_ML_DISABLED)+" method=\"get\" action=\""+CMSMappingsConfiguratorGenerator.getPath(section.getDocument(), CMSMappingsConfiguratorGenerator.ACTION_SWITCH_MULTILANGUAGE_INSTANCE)+"\">");
		appendString("<input type=\"hidden\" name=\"value\" value=\"false\"/><input type=\"hidden\" name=\"ts\" value=\"<%=System.currentTimeMillis()%>\"/><input type=\"hidden\" name=\"pId\" value=\"<bean:write name="+quote(CMSMappingsConfiguratorGenerator.getDialogFormName(currentDialog, section.getDocument()))+" property="+quote("id")+"/>\"/>");
		appendString("<a href=\"#\" class=\"button\" onclick=\"document."+ModuleBeanGenerator.FIELD_ML_DISABLED+".submit(); return false\"><span>Enable languages</span></a>");
		decreaseIdent();
		appendString("</div>");
		appendString("</form>");
		appendString("</logic:equal>");
	}

}