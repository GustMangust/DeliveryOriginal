using DeliveryOriginal.Admin.Core.Helpers;
using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize(Role = RoleGroup.Regulars)]
    public class DishController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public DishController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            var dishes = await UnitOfWork.DishRepository.GetAll();

            return View(dishes);
        }

        [HttpGet]
        public async Task<ActionResult> CreateDish()
        {
            var dish = GetDishVMFromDish(new Dish());

            SelectList categories = new SelectList(await UnitOfWork.CategoryRepository.GetAll(), "Id", "Name", dish.CategoryId);
            ViewBag.Categories = categories;

            return View(dish);
        }

        [HttpPost]
        public async Task<ActionResult> CreateDish(DishVM model)
        {
            var newDish = await GetDishFromDishVM(model);

            var file = GetRequestFirstFile(Request);
            if (file != null)
            {
                var fileExtension = Path.GetExtension(Request?.Files[0]?.FileName)?.ToLower();
                if (!ImageResizer.AcceptedImageFormats.Contains(fileExtension))
                {
                    ModelState.AddModelError("Location Logo", @"Error loading Location Logo. Please check if image is in BMP, GIF, EXIF, JPG, JPEG, PNG or TIFF format.");
                }
            }

            if (ModelState.IsValid)
            {
                if (file != null)
                {
                    try
                    {
                        var imageUrl = await ImageResizer.ScaleImageCrop(file.InputStream, 
                                                                        Int32.Parse(DeliveryOriginalSettings.DishImageWidth), 
                                                                        Int32.Parse(DeliveryOriginalSettings.DishImageHeight));
                        newDish.ImageUrl = imageUrl;
                    }
                    catch (Exception ex)
                    {
                        return View("Error", ex);
                    }
                }
            } 
            else
            {
                return View();
            }

            await UnitOfWork.DishRepository.Insert(newDish);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task<ActionResult> EditDish(int id)
        {
            var dish = GetDishVMFromDish(await UnitOfWork.DishRepository.Get(id));

            SelectList categories = new SelectList(await UnitOfWork.CategoryRepository.GetAll(), "Id", "Name", dish.CategoryId);
            ViewBag.Categories = categories;

            return View(dish);
        }

        [HttpPost]
        public async Task<ActionResult> EditDish(DishVM dish)
        {
            var updatedDish = await GetDishFromDishVM(dish);

            var file = GetRequestFirstFile(Request);
            if (file != null)
            {
                var fileExtension = Path.GetExtension(Request?.Files[0]?.FileName)?.ToLower();
                if (!ImageResizer.AcceptedImageFormats.Contains(fileExtension))
                {
                    ModelState.AddModelError("Location Logo", @"Error loading Location Logo. Please check if image is in BMP, GIF, EXIF, JPG, JPEG, PNG or TIFF format.");
                }
            }

            if (ModelState.IsValid)
            {
                if (file != null)
                {
                    try
                    {
                        var imageUrl = await ImageResizer.ScaleImageCrop(file.InputStream,
                                                                        Int32.Parse(DeliveryOriginalSettings.DishImageWidth),
                                                                        Int32.Parse(DeliveryOriginalSettings.DishImageHeight));
                        updatedDish.ImageUrl = imageUrl;
                    }
                    catch (Exception ex)
                    {
                        return View("Error", ex);
                    }
                }
            }
            else
            {
                return View();
            }

            await UnitOfWork.DishRepository.Update(updatedDish);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task DeleteDish(int dishId)
        {
            await UnitOfWork.DishRepository.Delete(dishId);
        }

        [HttpPost]
        public async Task<ActionResult> DeleteDishLogo(int dishId)
        {
            var dish = await UnitOfWork.DishRepository.Get(dishId);

            await MediaStorageHelper.DeleteFromCloudStorageAsync(dish.ImageUrl);
            dish.ImageUrl = "";

            await UnitOfWork.DishRepository.Update(dish);

            return new HttpStatusCodeResult(200);
        }

        private DishVM GetDishVMFromDish(Dish dish)
        {
            var newDish = new DishVM
            {
                Id = dish.Id,
                Name = dish.Name,
                Category = dish?.Category,
                Description = dish.Description,
                ImageUrl = dish.ImageUrl,
                Cost = dish.Cost
            };

            if (dish?.Category?.Id != null)
            {
                newDish.CategoryId = dish.Category.Id;
            }

            return newDish;
        }

        private async Task<Dish> GetDishFromDishVM(DishVM dish)
        {
            var category = await UnitOfWork.CategoryRepository.Get(dish.CategoryId);

            return new Dish
            {
                Id = dish.Id,
                Name = dish.Name,
                Category = category,
                Description = dish.Description,
                ImageUrl = dish.ImageUrl,
                Cost = dish.Cost
            };
        }

        public HttpPostedFileBase GetRequestFirstFile(HttpRequestBase request)
        {
            return request.Files.Count > 0 && request.Files[0] != null && request.Files[0].ContentLength > 0
                ? request.Files[0]
                : null;
        }
    }
}