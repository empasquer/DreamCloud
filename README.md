DO NOT MERGE!!

Har haft nogle tanker efter jeg lavede det her. Denne branch er ikke en god ide i sig selv, men den gav mig en god ide til senere.
Her laves om på hvordan vi skriver endpoints, så et endpoint nu ikke hedder /wishlist/{wishlistid}, men nu hedder /profile/{profileusername}/wishlist/{wishlistid}.
Hvis vi beholder dette kan man aldrig gå ind på en andens profil, wishlist eller wish fordi den altid vil filtrerer for om session username matcher URL username 
og redirecte til login hvis ikke det matcher.
Det duer ikke hvis vi skal kunne dele en wishlist med andre. 
Mit forslag er at vi ændre path'sne til at hedde:

/{profileusername}/profile
/{profileusername}/wishlist{wishlistId}
/{profileusername}/wishlist{wishlistId}/wish{wishId}

Hvis vi gør dette kan vi stadig filtrerer for at man ikke kan gå ind på andres profiler, men man kan godt gå ind på det andet. 
Derduover er pointen at hvis vi har {profileusername} i alle URL'er kan alle views vises forskelligt basseret på om session username matcher URL username. 
Så hvis vi når dertil hvor man skal kunne reserverer en andens ønsker vil det kunne lade sig gøre. 
Har ikke nået længerer end at tænkte det, men forestiller mig det er noget lignende:

    @GetMapping("/{profileUsername}/wishlist/{wishlistId}")
    public String wishlist(@PathVariable String profileUsername, @PathVariable int wishlistId, Model model, HttpSession session) {
         String loggedInUsername = String.valueOf(session.getAttribute("username"));
             
              if (!loggedInUsername.equals(profileUsername)) {
                  model.addAttribute("owner", true)
              }
              else
                  model.addAttribute("owner, false)
    
             (og resten af loggiken i controlleren ofc)

        return "home/wishlist";
    }

    Og når vi så kommer til view så kan man have noget: 
     <th:block th:if="owner">
        <h1>Welcome to your profile</h1>
        <p options: edit, delete></p>
        whatever man kan som owner
    </th:block>

    <th:block th:unless="owner">
        <h1>Welcome to UserA profile</h1>
        <p options: reserve></p>
    </th:block>


  90% procent af siden vil være det samme, men knapperne for rediger/delete/reserver er forsekllige.

