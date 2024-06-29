package com.example.apptodo.legacy_view

/*

class ItemListFragment : Fragment(), CheckBoxListener, EditItemListener {

*/
/*    private val viewModel: ItemListViewModel by viewModels() {
        ItemListViewModel.factory((requireActivity().application as App).todoItemsRepository)
    }*//*


    private lateinit var binding: FragmentItemListBinding

    private val adapter = ItemsAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
*/
/*
        init()
*//*


*/
/*
        viewModel.items.observe(viewLifecycleOwner) {
            binding.itemsRecycler.post {
                adapter.update(it)
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.counter.observe(viewLifecycleOwner) {
            binding.doneItemCounterText.text = getString(R.string.done_items, it)
        }

        viewModel.getItems()

        binding.floatingButton.setOnClickListener {
            changeFragment(RedactorFragment())
        }

        binding.achievementVisible.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.getItems(isChecked)
        }
*//*



    }


*/
/*    private fun init() {
        binding.apply {
            itemsRecycler.layoutManager = LinearLayoutManager(context)
            itemsRecycler.adapter = adapter
            swipeToGesture(itemsRecycler)
        }
    }*//*


*/
/*    private fun swipeToGesture(recyclerView: RecyclerView) {
        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.deleteItem(position, binding.achievementVisible.isChecked)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }*//*


*/
/*
    override fun onCheckBoxClicked(
        item: TodoItem, isChecked: Boolean
    ) {
        viewModel.checkItem(item, isChecked)
    }
*//*


    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity()
            .supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_place, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onItemEdit(id: String) {
        changeFragment(RedactorFragment.newInstance(id))
    }

}
*/
