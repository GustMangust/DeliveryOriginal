﻿using System;
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

    public enum OrderOrderBy
    {
        SubmittedAtAsc = 0,
        SubmittedAtDesc = 1,
        OrderNumberAsc = 2,
        OrderNumberDesc = 3
    }

    public enum OrderStatus
    {
        [Display(Name = "New")]
        New = 0,
        [Display(Name = "Ready For Cooking")]
        ReadyForCooking = 1,
        [Display(Name = "Cooking")]
        Cooking = 2,
        [Display(Name = "Ready For Delivery")]
        ReadyForDelivery = 3,
        [Display(Name = "In Delivery")]
        InDelivery = 4,
        [Display(Name = "Delivered")]
        Delivered = 5,
        [Display(Name = "Declined")]
        Declined = 6
    }
}