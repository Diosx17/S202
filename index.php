<!DOCTYPE html>
<html lang="en">
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <link type="text/css" rel="stylesheet" href="css/style.css">
  <script src="script/apparition.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width">
  <title>VikiSearch</title>
</head>
<body>
  
  <header>
    <nav class="navigation">
      <a><img src="img/logo.png" id="logo"></a>
    </nav>
  </header>

  <form method="post">
    <div class="search-box">
      <input type="text" name="q" placeholder="Veuillez entrer votre recherche">
      <button type="submit"><i class="fa fa-search"></i></button>
    </div>
  </form>

  <div class="bloc-historique"> 
    <div class="icone">

    </div>
    <div class="icone">
      
    </div>
    <div class="icone">
      
    </div>
    <div class="icone">
      
    </div>
    <div class="icone">
      
    </div>
    <div class="icone">
      
    </div>
  </div>

  <div id="corps">
 
    <div class="reveal">

      <div class="random-article">
        <div class="article">
          <img src="img/viki1.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
        <div class="article">
          <img src="img/viki2.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
        <div class="article">
          <img src="img/viki3.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
        <div class="article">
          <img src="img/viki1.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
        <div class="article">
          <img src="img/viki2.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
        <div class="article">
          <img src="img/viki3.jpg" alt="image">
          <div class="article-content">
            <h2>Titre</h2>
            <p>Description</p>
            <a href="">Lien</a>
          </div>
        </div>
      </div>
      <?php 
      $exe_path = ('./main.exe');

      
      if(isset($_POST['q']))
      {
        $cmd = $exe_path;
        $output = shell_exec($cmd);

        $array_output = explode("\n", $output);

        // Parcourir le tableau et afficher les résultats dans un nouveau bloc
        foreach($array_output as $value)
        {
          echo '<div class="rectangle">';
          echo '<img src="img/test.jpg" alt="image">';
          echo '<div class="article-content">';
          echo '<h2>'.$value.'</h2>';
          echo '<p>Description</p>';
          echo '<a href="">Lien</a>';
          echo '</div>';
          echo '</div>';
        }
  
       
        
      }
    ?>

  </div>

</body>
</html>