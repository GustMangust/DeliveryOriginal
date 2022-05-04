using DeliveryOriginal.Admin.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Core.Identity
{
    public class CustomAuthorizeAttribute : AuthorizeAttribute
    {
        public CustomAuthorizeAttribute(params RoleGroup[] roles) : base()
        {
            AuthorizeRoles = roles;
        }

        protected virtual CustomPrincipal CurrentUser
        {
            get { return HttpContext.Current.User as CustomPrincipal; }
        }

        public RoleGroup Role { get; set; }
        public RoleGroup[] AuthorizeRoles { get; set; }

        protected override bool AuthorizeCore(HttpContextBase httpContext)
        {
            if (CurrentUser != null)
            {
                foreach (var role in AuthorizeRoles)
                {
                    if (CurrentUser.IsInRole(role))
                    {
                        return true;
                    }
                }
            }
            return (CurrentUser != null && CurrentUser.IsInRole(Role)) ? true : false;
        }

        protected override void HandleUnauthorizedRequest(AuthorizationContext filterContext)
        {
            RedirectToRouteResult routeData = null;

            if (CurrentUser == null)
            {
                routeData = new RedirectToRouteResult
                    (new System.Web.Routing.RouteValueDictionary
                    (new
                    {
                        controller = "Account",
                        action = "Login",
                    }
                    ));
            }
            else
            {
                routeData = new RedirectToRouteResult
                (new System.Web.Routing.RouteValueDictionary
                 (new
                 {
                     controller = "Error",
                     action = "AccessDenied"
                 }
                 ));
            }

            filterContext.Result = routeData;
        }

    }
}