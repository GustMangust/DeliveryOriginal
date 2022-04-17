using DeliveryOriginal.Admin.Models;
using Microsoft.AspNet.Identity.EntityFramework;

namespace DeliveryOriginal.Admin.Models
{
    public class User
    {
        public int Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string FullName { get; set; }
        public RoleGroup Role { get; set; }
    }
}
