using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace DeliveryOriginal.Admin.Models
{
    public enum RoleGroup
    {
        [Display(Name = "Administrators")]
        Administrators = 0,
        [Display(Name = "System Administrators")]
        SystemAdministrators = 1,
        [Display(Name = "Analytics Administrators")]
        AnalyticsAdministrators = 2,
        [Display(Name = "Regulars")]
        Regulars = 3,
        [Display(Name = "Cooks")]
        Cooks = 4,
        [Display(Name = "Deliverymens")]
        Deliverymens = 5
    }
}