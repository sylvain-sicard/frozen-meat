Vue.component('menu-component',{
  data () {
    return {
      items : []
    }
  },
  template:`
  <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="menu-component">
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html"> 
      <div class="sidebar-brand-icon "> 
        <img src="img/ic_logo-web.png" width="50"> 
      </div> 
      <div class="sidebar-brand-text mx-3">Frozen Meat</div> 
    </a> 
    <hr class="sidebar-divider my-0"/>
    <li class="nav-item">
      <a class="nav-link" href="/list-edit.html"> 
        <span>New list!</span> 
      </a> 
    </li>
    <hr class="sidebar-divider my-0"/> 
    <div v-for="item in items">
      <li class="nav-item" :class="isActive()"> 
        <a class="nav-link" :href="'/list.html?id='+item.id"> 
          <span>{{item.name}}</span> 
          <span v-if="item.products.length" class="badge badge-success badge-pill">{{item.products.length}}</span>
        </a> 
      </li> 
      <hr class="sidebar-divider"/> 
    </div> 
  </ul>`,
  mounted () {
    axios
      .get('/list/all')
      .then(response => {
        this.items = response.data;
      });
  },
  methods:{
    isActive: function(){
      return false;
    }
  }
})
