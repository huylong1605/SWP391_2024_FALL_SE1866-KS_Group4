const hamBurger = document.querySelector(".toggle-btn");

hamBurger.addEventListener("click", function () {
  document.querySelector("#sidebar").classList.toggle("/");
});

//   function toggleStatus(userId, element) {
//   const xhr = new XMLHttpRequest();
//   xhr.open("POST", "${pageContext.request.contextPath}/Views/Admin/accountManage", true);
//   xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//
//   xhr.onload = function() {
//   if (xhr.status === 200) {
//   // Update UI based on the new status
//   const currentColor = element.querySelector('i').style.color;
//   element.querySelector('i').style.color = currentColor === 'rgb(2, 159, 2)' ? 'red' : '#029f02';
//   const statusText = element.closest('tr').querySelector('td:nth-child(5)');
//   statusText.textContent = statusText.textContent === 'Active' ? 'Deactive' : 'Active';
//   statusText.style.color = statusText.style.color === 'lawngreen' ? 'red' : 'lawngreen';
// } else {
//   console.error('Error toggling status: ' + xhr.statusText);
// }
// };
//
//   xhr.send("userId=" + userId);
// }
