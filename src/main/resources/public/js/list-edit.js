
const app = new Vue({
  el: '#wrapper',
  
  data() {
    return {
      list: {}
    };
  },
  methods: {
      
  },
  mounted () {
  	let uri = window.location.search.substring(1); 
    let params = new URLSearchParams(uri);
    
    if(params.has('id')){
      axios
        .get('/list/'+params.get("id"))
        .then(response => {
          this.list = response.data;
        });
    }
  },
  methods: {
    save: function () {
        axios
        .post('/list/add', this.list)
        .then(response => {
          this.list = response.data;
          window.location.href='/list.html?id='+this.list.id;
        });
  
    },
    remove: function () {
      axios
        .delete('/list/'+this.list.id)
        .then(response => {
          this.list = {};
          window.location.href='/index.html';
        });
    }
  }
});


