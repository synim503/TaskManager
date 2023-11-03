let addTaskButtonPopup = document.querySelector('.add-button-popup');
let popupBg = document.querySelector('.popup_bg');
let popup = document.querySelector('.popup');
let addTaskButton = document.querySelector('.add-button');
let closePopupAddTask = document.querySelector('.close-popup')
let closePopupEdit = document.querySelector('.close-popup-edit')
let notifyButton = document.querySelector('.notify_button');
let notifyBox = document.querySelector('.notification');

notifyButton.addEventListener('click', e=>{
    if(notifyBox.classList.contains('active')){
        notifyBox.classList.remove('active');
    }else {
        notifyBox.classList.add('active');
    }

})

addTaskButtonPopup.addEventListener('click', (e)=>{
    let descr = document.querySelector('.descr_lable').value;
    const radioButtons = document.querySelectorAll('input[name="radios"]');
    let selected;
    for (const radioButton of radioButtons) {
        if (radioButton.checked) {
            selected = radioButton.value;
        }
    }
    let duedate = document.querySelector('.duedate').value;
    if(validateValueAddTaskPopup(descr,duedate,selected)){
        return;
    };
    addTask(descr,duedate,selected);
    alert('Задача добавлена!')
    clearPopupWind();
})

closePopupAddTask.addEventListener('click', (e)=>{
    popupBg.classList.remove('active');
    popup.classList.remove('active');
})

closePopupEdit.addEventListener('click', (evt => {
        popupEdit.classList.remove('active');
        popupBgEdit.classList.remove('active');
}))

addTaskButton.addEventListener('click',(e)=>{
    e.preventDefault()
    popupBg.classList.add('active');
    popup.classList.add('active')
})

document.addEventListener('click', (e)=>{
    if(e.target===popupBg){
        popupBg.classList.remove('active');
        popup.classList.remove('active');
    }

})

function validateValueAddTaskPopup(descr,duedate,selected){
    if(descr===''){
        alert('Поле описания задачи не заполнено!');
        return true;
    }else if(duedate===''){
        alert('Поле даты не заполнено!');
        return true;
    }else {
        return false
    }
}
function clearPopupWind(){
    document.querySelector('.descr_lable').value ='';
    document.querySelector('.duedate').value='';
    popupBg.classList.remove('active');
    popup.classList.remove('active');
}
function addTask(descr, duedate, importance){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "descr": descr,
        "dueDate": duedate,
        "importance": importance
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/api/v1/tasks/add", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

function makeAllTask(tasks) {

      document.querySelector('.content').innerHTML = `<div id="tasks" class="tasks"></div>`
      for (key in tasks) {
          let div = document.createElement('div');
          div.className = "task_item";
          div.id = tasks[key].id;
          div.onclick = e => taskItemClick(event);

          let color = 'white';
          if (tasks[key].status === 'AWAITING') {
              color = 'rgb(243,239,116)';
          } else if (tasks[key].status == 'CANCELLED') {
              color = 'rgb(245,114,116)';
          } else {
              color = 'rgb(114,245,94)';
          }

          div.innerHTML = `
                     <div class="task_content">
                         <div style="background-color: ${color}" class="status-icon"></div>
                         <div class="descr">${tasks[key].descr}</div>
                         <div class="inf">
                             <div class = "block_inf_item">
                                 <div class="importance">Важность: </div>
                                 <div class="importance_value">${tasks[key].importance}</div>
                             </div>
                                 <div class = "block_inf_item">
                                 <div class="dueDate">Дата: </div>
                                 <div class="dueDate_value">${tasks[key].dueDate}</div>
                             </div>
                             <div class = "block_inf_item">
                                 <div class="status">Статус: </div>
                                 <div class="status_value">${tasks[key].status}</div>
                             </div>
                         </div>
                     </div>`;
          document.querySelector('.tasks').appendChild(div);

      }

  }

function findTaskSort() {

      let dateFrom = document.querySelector('.date-from').value;
      let dateTo = document.querySelector('.date-to').value

      const radioButtons = document.querySelectorAll('input[name="importance"]');
      let selectedSize;
      for (const radioButton of radioButtons) {
          if (radioButton.checked) {
              selectedSize = radioButton.value;
          }
      }
      let descr = document.querySelector('.find-text-descr').value;
      getTaskQuery(descr, dateFrom, dateTo, selectedSize);
  }

function getTaskQuery(descr, dateFrom, dateTo, importance) {
      if (importance == null) {
          importance = '';
      }
      var requestOptions = {
          method: 'GET',
          redirect: 'follow'
      };

      fetch(`/api/v1/tasks/?descr=${descr}&dueFrom=${dateFrom}&dueTo=${dateTo}&importance=${importance}`, requestOptions)
          .then(response => response.json())
          .then(result => makeAllTask(result))
          .catch(error => console.log('error', error));
  }

    let popupBgEdit = document.querySelector('.popup_bg_edit');
    let popupEdit = document.querySelector('.popup_edit');
    let popupEditSaveButton = document.querySelector('.edit-button-popup');

    popupEditSaveButton.addEventListener('click', (event)=>{
        let newDescr = document.querySelector('.descr_lable_edit').value;
        let newDueDate = document.querySelector('.duedate_edit').value;
        let id =  document.querySelector('.popup_content').id;

        const radioButtons = document.querySelectorAll('input[name="radio_edit"]');
        let selectedImportance;
        for (const radioButton of radioButtons) {
            if (radioButton.checked) {
                selectedImportance = radioButton.value;
            }
        }

        if(validateValueAddTaskPopup(newDescr,newDueDate,selectedImportance)){
            return;
        }else{
            if(confirm('Подтвердите изменения!')){
                updateApiRequest(id, newDescr,newDueDate,selectedImportance);
                popupBgEdit.classList.remove('active');
                popupEdit.classList.remove('active');
            }
        }
    })

  function taskItemClick(event) {
      let id = event.target.id;
      let descr = event.target.querySelector('.descr').innerHTML;
      let importance = event.target.querySelector('.importance_value').innerHTML;
      let dueDate = event.target.querySelector('.dueDate_value').innerHTML;

      const radioButtons = document.querySelectorAll('input[name="radio_edit"]');
      for (const radioButton of radioButtons) {
          if (radioButton.value===importance) {
              radioButton.checked = true;
          }
      }

      document.querySelector('.descr_lable_edit').value = descr;
      document.querySelector('.duedate_edit').value = dueDate;

      popupBgEdit.classList.add('active');
      popupEdit.classList.add('active');

      document.querySelector('.popup_content').id = id;

  }

  function updateApiRequest(id, descr,dueDate, importance){
      var formdata = new FormData();
      formdata.append("id", id);
      formdata.append("descr", descr);
      formdata.append("dueDate", dueDate);
      formdata.append("importance", importance);

      var requestOptions = {
          method: 'PUT',
          body: formdata,
          redirect: 'follow'
      };

      fetch("/api/v1/tasks/update", requestOptions)
          .then(response => response.text())
          .then(result => console.log(result))
          .catch(error => console.log('error', error));
  }


const evtSource = new EventSource("/api/v1/tasks/notifications", { withCredentials: true } );

evtSource.onmessage = function(event) {
    let obj = JSON.parse(event.data);
    console.log(obj);

    let div = document.createElement('div');
    div.className = 'notification_item';
    div.id = obj.taskId;

    let period_value;


    if(obj.importance==='LOW'){
       period_value='PT2H';
    } else if(obj.importance==='HIGH'){
        period_value='PT24H';
    }else {
        period_value='PT72H';
    }
    div.innerHTML =
        `
                <div class="descr_notify">${obj.descr}</div>
                <div class="button_notify">
                    <button onclick="completed(event)" class="completed_button_notify">Завершить</button>
                    <button onclick="canceled(event)" class="canceled_button_notify">Отменить</button>
                    <button onclick="postpone(event)" value="${period_value}" class="postpone_button_notify">Отложить</button>
                    
                </div>
         `
    document.querySelector('.notification').appendChild(div);
}

function completed(event){
    let delBlock = event.target.parentNode.parentNode;
    let id = delBlock.id;
    console.log(id);
    delBlock.parentNode.removeChild(delBlock);
    var requestOptions = {
        method: 'PUT',
        redirect: 'follow'
    };

    fetch(`/api/v1/tasks/complete?id=${id}`, requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

function canceled(event){
    let delBlock = event.target.parentNode.parentNode;
    let id = delBlock.id;
    console.log(id);
    delBlock.parentNode.removeChild(delBlock);
    var requestOptions = {
        method: 'PUT',
        redirect: 'follow'
    };

    fetch(`/api/v1/tasks/cancel?id=${id}`, requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));

}

function postpone(event){
    let period = event.target.value;
    let delBlock = event.target.parentNode.parentNode;
    let id = delBlock.id;
    console.log(id);
    delBlock.parentNode.removeChild(delBlock);

    var formdata = new FormData();

    var requestOptions = {
        method: 'PUT',
        body: formdata,
        redirect: 'follow'
    };

    fetch(`/api/v1/tasks/postpone?id=${id}&period=${period}`, requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

