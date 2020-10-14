
const app = new Vue({
  el: '#wrapper',
  
  data() {
    return {
      list: null
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
  methods:{
    updateQty: function(listId, productId, qty){
      axios
        .get('/list/update-qty/'+listId+'/'+productId+'/'+qty)
        .then(response => {
          this.list = response.data;
        });
    }
  }
});


