using DeliveryOriginal.Admin.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Web;

namespace DeliveryOriginal.Admin.Core.Identity
{
    public class CustomPrincipal : IPrincipal
    {
        #region Identity Properties

        public int Id { get; set; }
        public string Login { get; set; }
        public string FullName { get; set; }
        public RoleGroup Role { get; set; }
        #endregion

        public IIdentity Identity
        {
            get; private set;
        }

        public bool IsInRole(string role)
        {
            throw new NotImplementedException();
        }

        public bool IsInRole(RoleGroup role)
        {
            if (Role == role)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public CustomPrincipal(string login)
        {
            Identity = new GenericIdentity(login);
        }
    }
}