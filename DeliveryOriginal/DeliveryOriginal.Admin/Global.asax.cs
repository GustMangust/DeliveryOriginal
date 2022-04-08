using DeliveryOriginal.Admin.Identity;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using System.Web.Security;

namespace DeliveryOriginal.Admin
{
    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
        }

        protected void Application_PostAuthenticateRequest(Object sender, EventArgs e)
        {
            HttpCookie authCookie = Request.Cookies["sc"];
            if (authCookie != null && !string.IsNullOrEmpty(authCookie.Value))
            {
                FormsAuthenticationTicket authTicket = FormsAuthentication.Decrypt(authCookie.Value);

                var serializeModel = JsonConvert.DeserializeObject<User>(authTicket.UserData);
                CustomPrincipal principal = new CustomPrincipal(authTicket.Name);
                principal.Id = serializeModel.Id;
                principal.Login = serializeModel.Login;
                principal.FullName = serializeModel.FullName;
                principal.Role = serializeModel.Role;

                HttpContext.Current.User = principal;
            }
        }
    }
}
