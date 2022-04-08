using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Web;

namespace DeliveryOriginal.Admin.Identity
{
    public class CustomPrincipal : IPrincipal
    {
        #region Identity Properties

        public int Id { get; set; }
        public string Login { get; set; }
        public string FullName { get; set; }
        public string Role { get; set; }
        #endregion

        public IIdentity Identity
        {
            get; private set;
        }

        public bool IsInRole(string role)
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