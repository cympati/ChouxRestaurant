dataDetail: [
        {
          // User
          firstName: "Patiphon",
          lastName: "Klangpraphan",
          id: "234",
          email: "chancreamz@gmail.com",
          password: "28052545",
          phoneNumber: "0956600463",
          reserveDetails: [
            {
              reserveId: "001",
              date: {
                year: 2002,
                month: 5,
                day: 28,
              },
              time: "11:00",
              partySize: 2,
              specialRequests: "Hungry so much!",
              status: "upcoming",
              statusComplete: false,
              statusCancel: false,
            },
            {
              reserveId: "313",
              date: {
                year: 2001,
                month: 2,
                day: 9,
              },
              time: "09:00",
              partySize: 6,
              specialRequests: "dadsdsdsdsadsadsdadss",
              status: "cancel",
              statusComplete: false,
              statusCancel: true,
            },
          ],
        },
        {
          // Admin
          firstName: "Jirasin",
          lastName: "Kimuji",
          id: "122",
          email: "Jirasin@mail.kmutt.ac.th",
          password: "1212312121",
          phoneNumber: "0888888888",
        },
      ],




      <v-list-item
          class="mt-3"
          v-for="(page, i) in pages"
          :key="i"
          :to="page.to"
        >
          <div class="d-flex justify-space-between">
            <v-icon
              v-text="page.icon"
              class="text-decoration-none mr-3"
            ></v-icon>
            <v-list-item-title
              v-text="page.namePage"
              class="text-decoration-none"
              style="font-size: 1.3rem"
            ></v-list-item-title>
          </div>
        </v-list-item>





        infoDetails: {
        // User
        firstName: "Patiphon",
        lastName: "Klangpraphan",
        id: "234",
        email: "cympati@gmail.com",
        password: "28052545",
        phoneNumber: "0956600463",
        isAdmin: 0,
      },
      reserveDetails: [{
        reserveId: "000",
        date: {
          year: "",
          month: "",
          day: "",
        },
        time: "",
        partySize: "",
        specialRequests: "",
        // 0 = pending, 1 = come, 2 = not come, 3 = complete, 4 = cancel
        status: 0,
      },]


      infoDetails: [
        {
          id: "001",
          detail: {
            firstName: "Patiphon",
            lastName: "Klangpraphan",
            partySize: 2,
            email: "chancreamz@gmail.com",
            phoneNumber: "0956600463",
            specialRequests: "Hungry so much!",
          },
          date: {
            year: 2002,
            month: 5,
            day: 28,
          },
          time: "11:00",
          status: "upcoming",
          statusComplete: false,
          statusCancel: false,
        },
        {
          id: "122",
          detail: {
            firstName: "Jirasin",
            lastName: "Kimuji",
            partySize: 4,
            email: "Jirasin@mail.kmutt.ac.th",
            phoneNumber: "0888888888",
            specialRequests: "asdasdadadwdwadad",
          },
          date: {
            year: 2002,
            month: 7,
            day: 12,
          },
          time: "10:00",
          status: "complete",
          statusComplete: true,
          statusCancel: false,
        },
        {
          id: "313",
          detail: {
            firstName: "Apisit",
            lastName: "Maneerat",
            partySize: 6,
            email: "Apisit@mail.kmutt.ac.th",
            phoneNumber: "0987654321",
            specialRequests: "dadsdsdsdsadsadsdadss",
          },
          date: {
            year: 2001,
            month: 2,
            day: 9,
          },
          time: "09:00",
          status: "cancel",
          statusComplete: false,
          statusCancel: true,
        },
        {
          id: "022",
          detail: {
            firstName: "Monthara",
            lastName: "Kiatruangchai",
            partySize: 4,
            email: "Monthara.k@mail.kmutt.ac.th",
            phoneNumber: "08888888",
            specialRequests: "Bla Bla Bla...",
          },
          date: {
            year: 2001,
            month: 8,
            day: 26,
          },
          time: "18:00",
          status: "upcoming",
          statusComplete: false,
          statusCancel: false,
        },
      ],



      allReserveForUser: {
        userInfo: {
          // User
          firstName: "Patiphon",
          lastName: "Klangpraphan",
          id: "234",
          email: "cympati@gmail.com",
          password: "28052545",
          phoneNumber: "0956600463",
          isAdmin: 0,
        },

        allReserve: [
          {
            reserveId: "001",
            date: {
              year: "2002",
              month: "5",
              day: "28",
            },
            time: "11:00",
            partySize: "2",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
          {
            reserveId: "122",
            date: {
              year: "2002",
              month: "7",
              day: "12",
            },
            time: "10:00",
            partySize: "4",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 3,
          },
          {
            reserveId: "313",
            date: {
              year: "2001",
              month: "2",
              day: "9",
            },
            time: "09:00",
            partySize: "6",
            specialRequests: "dadsdsdsdsadsadsdadss",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 4,
          },
          {
            reserveId: "022",
            date: {
              year: "2001",
              month: "8",
              day: "26",
            },
            time: "18:00",
            partySize: "4",
            specialRequests: "Bla Bla Bla...",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
        ],
      },