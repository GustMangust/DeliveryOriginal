using DeliveryOriginal.Admin.Models;
using System;
using System.Web.Security;

namespace DeliveryOriginal.Admin.Core.Identity
{
    public class CustomMembershipUser : MembershipUser
    {
        #region User Properties

        public int Id { get; set; }
        public string Login { get; set; }
        public string FullName { get; set; }
        public RoleGroup Role { get; set; }

        #endregion

        public CustomMembershipUser(User user) : base("CustomMembership", user.Login, user.Id, string.Empty, string.Empty, string.Empty, true, false, DateTime.Now, DateTime.Now, DateTime.Now, DateTime.Now, DateTime.Now)
        {
            Id = user.Id;
            Login = user.Login;
            FullName = user.FullName;
            Role = user.Role;
        }
    }
}