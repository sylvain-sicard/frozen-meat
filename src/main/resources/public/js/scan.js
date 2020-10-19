setTimeout(function(){$('#myModal').modal('hide')},3000);

const app = new Vue({
  el: '#wrapper',
  
  data() {
    return {
      lists: null,
      product: null
    };
  },
  methods: {
      
  },
  mounted () {

    let uri = window.location.search.substring(1); 
    let params = new URLSearchParams(uri);
    
    if(params.has('code')){
      axios
        .get('/product/code/'+params.get("code"))
        .then(response => {
          this.product = response.data;
          if(this.product.productName === undefined || this.product.productName == null || this.product.productName == ''){
            //window.location.href='/product-edit.html?code='+this.product.code;
            window.location.href='https://world.openfoodfacts.org/cgi/product.pl?code='+this.product.code;
          }
        });
    }
    axios
        .get('/list/all')
        .then(response => {
          this.lists = response.data;
        });
  },
  methods:{
    updateQty: function(listId, productId, qty){
      axios
        .get('/list/add-qty/'+listId+'/'+productId+'/'+qty)
        .then(response => {
          $('#myModal').modal();
        });
    }
  }
});


