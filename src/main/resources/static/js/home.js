$(document).ready(function () {
  loadPokemon();
  addPokemon();
  updatePokemon();
});

function loadPokemon() {
  clearPokeTable();

  var contentRows = $("#contentRows");

  $.ajax({
    type: "GET",
    url: "http://localhost:8080/pokedex/cards",
    success: function (cardList) {
      $.each(cardList, function (index, card) {
        var name = card.name;
        var set = card.set.setName;
        var cardNumber = card.cardNumber;
        var qty = card.quantity;
        var cardId = card.cardId;

        var isRare;
        if (card.rare == true) {
          isRare = "Yes";
        } else {
          isRare = "No";
        }

        var row = "<tr>";
        row += "<td>" + name + "</td>";
        row += "<td>" + set + "</td>";
        row += "<td>" + cardNumber + "</td>";
        row += "<td>" + isRare + "</td>";
        row += "<td>" + qty + "</td>";
        row +=
          '<td><button type="button" class="btn btn-info" onClick="showEditForm(' +
          cardId +
          ')">Edit</button></td>';
        row +=
          '<td><button type="button" class="btn btn-danger" onClick="deletePokemon(' +
          cardId +
          ')">Delete</button></td>';
        row += "</tr>";

        contentRows.append(row);
      });
    },
    error: function () {
      $("#errorMessages").append(
        $("<li>")
          .attr({ class: "list-group-item list-group-item-danger" })
          .text("Error calling web service. Please try again later.")
      );
    },
  });
}

function addPokemon() {
  $("#addPokemon").click(function (event) {
    var haveValidationErrors = checkAndDisplayValidationErrors(
      $("#addForm").find("input")
    );

    if (haveValidationErrors) {
      return false;
    }

    $.ajax({
      type: "POST",
      url: "http://localhost:8080/pokedex/card",
      data: JSON.stringify({
        name: $("#addName").val(),
        cardNumber: $("#addNumber").val(),
        rare: $("#rare-true").prop("checked") ? true : false,
        quantity: $("#addQty").val(),
        set: {
          setId: $("#selectSet option:selected").val(),
          setName: $("#selectSet option:selected").text(),
        },
      }),
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      dataType: "json",
      success: function () {
        $("#errorMessages").empty();
        $("#addName").val("");
        $("#selectSet").val("Select");
        $("#rare-true").prop("checked", false);
        $("#rare-false").prop("checked", false);
        $("#addQty").val("");
        loadPokemon();
      },
      error: function () {
        $("#errorMessages").append(
          $("<li>")
            .attr({ class: "list-group-item list-group-item-danger" })
            .text("Error calling web service. Please try again later.")
        );
      },
    });
  });
}

function clearPokeTable() {
  $("#contentRows").empty();
}

function showEditForm(cardId) {
  $("#errorMessages").empty();

  $.ajax({
    type: "GET",
    url: "http://localhost:8080/pokedex/card/" + cardId,
    success: function (card, status) {
      $("#editName").val(card.name);
      $("#editSet").val(card.set.setId);
      $("#editNumber").val(card.cardNumber);
      card.rare
        ? $("#editRare-true").prop("checked", true)
        : $("#editRare-false").prop("checked", true);
      $("#editQty").val(card.quantity);
      $("#editPokemonId").val(card.cardId);
    },
    error: function () {
      $("#errorMessages").append(
        $("<li>")
          .attr({ class: "list-group-item list-group-item-danger" })
          .text("Error calling web service. Please try again later.")
      );
    },
  });

  $("#pokemonTableDiv").hide(); // hide "Add Pokemon" table
  $("#editFormDiv").show(); // dispaly the edit
}

function hideEditForm() {
  $("#errorMessages").empty();

  $("#editName").val("");
  $("#editSet").val("Select");
  $("#editNumber").val("");
  $("#editRare-true").prop("checked", false);
  $("#editRare-false").prop("checked", false);
  $("#editQty").val("");

  $("#pokemonTableDiv").show();
  $("#editFormDiv").hide();
}

function updatePokemon(cardId) {
  $("#updateButton").click(function (event) {
    var haveValidationErrors = checkAndDisplayValidationErrors(
      $("#editForm").find("input")
    );

    if (haveValidationErrors) {
      return false;
    }

    $.ajax({
      type: "PUT",
      url: "http://localhost:8080/pokedex/card/" + $("#editPokemonId").val(),
      data: JSON.stringify({
        cardId: $("#editPokemonId").val(),
        name: $("#editName").val(),
        cardNumber: $("#editNumber").val(),
        quantity: $("#editQty").val(),
        rare: $("#editRare-true").prop("checked") ? true : false,
        set: {
          setId: $("#editSet option:selected").val(),
          setName: $("#editSet option:selected").text(),
        },
      }),
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      dataType: "json",
      success: function () {
        $("#errorMessages").empty();
        hideEditForm();
        loadPokemon();
      },
      error: function () {
        $("#errorMessages").append(
          $("<li>")
            .attr({ class: "list-group-item list-group-item-danger" })
            .text("Error calling web service. Please try again later.")
        );
      },
    });
  });
}

function deletePokemon(cardId) {
  console.log("url: http://localhost:8080/pokedex/card/" + cardId);
  $.ajax({
    type: "DELETE",
    url: "http://localhost:8080/pokedex/card/" + cardId,
    success: function () {
      loadPokemon();
    },
  });
}

function checkAndDisplayValidationErrors(input) {
  $("#errorMessages").empty();

  var errorMessages = [];

  input.each(function () {
    if (!this.validity.valid) {
      var errorField = $("label[for=" + this.id + "]").text();
      errorMessages.push(errorField + " " + this.validationMessage);
    }
  });

  if (errorMessages.length > 0) {
    $.each(errorMessages, function (index, message) {
      $("#errorMessages").append(
        $("<li>")
          .attr({ class: "list-group-item list-group-item-danger" })
          .text(message)
      );
    });
    return true; // indicating there were errors
  } else {
    return false; // indicating there were no errors
  }
}
